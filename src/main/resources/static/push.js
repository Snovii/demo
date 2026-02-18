async function enablePush() {

    const permission = await Notification.requestPermission();

    console.log("Permission status:", permission);

    if (permission !== 'granted') {
        alert("Permission denied");
        return;
    }

    const registration = await navigator.serviceWorker.register('/sw.js');

    const publicKey = await fetch('/api/push/public-key')
        .then(res => res.text());

    const convertedKey = urlBase64ToUint8Array(publicKey);

    const subscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: convertedKey
    });

    await fetch('/api/push/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(subscription)
    });

    alert("Push enabled!");
}

function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
        .replace(/-/g, '+')
        .replace(/_/g, '/');

    const rawData = window.atob(base64);
    return Uint8Array.from([...rawData].map(char => char.charCodeAt(0)));
}