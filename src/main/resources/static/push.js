function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
        .replace(/-/g, '+')
        .replace(/_/g, '/');

    const rawData = window.atob(base64);
    return Uint8Array.from([...rawData].map(char => char.charCodeAt(0)));
}

async function enablePush() {
console.log("enablePush triggered");
    try {
        const permission = await Notification.requestPermission();
        console.log("Permission status:", permission);

        if (permission !== 'granted') {
            alert("Permission denied");
            return;
        }

        const registration = await navigator.serviceWorker.register('/sw.js');
        console.log("Service Worker registered");

        const publicKey = await fetch('/api/push/public-key')
            .then(res => res.text());
        console.log("Public key received");

        const convertedKey = urlBase64ToUint8Array(publicKey);

        const subscription = await registration.pushManager.subscribe({
            userVisibleOnly: true,
            applicationServerKey: convertedKey
        });
        console.log("Subscription created",JSON.stringify(subscription));

        await fetch('/notifications/subscribe', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(subscription)
        });

        console.log("Subscription sent to backend");

    } catch (error) {
        console.error("ERROR OCCURRED:", error);
    }
}