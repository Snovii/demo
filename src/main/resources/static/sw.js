console.log("SW file parsed");

self.addEventListener('install', event => {
    console.log("SW installed");
    self.skipWaiting();
});

self.addEventListener('activate', event => {
    console.log("SW activated");
});

self.addEventListener('push', event => {
    let data = { title: "Notification", message: "Default message" };
    try {
        data = event.data.json();
    } catch (e) {
        console.error("Failed to parse push data", e);
    }

    event.waitUntil(
        self.registration.showNotification(data.title, {
            body: data.message
        })
    );
});
