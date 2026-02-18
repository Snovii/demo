self.addEventListener('push', function(event) {

    console.log("Push event received");

    let title = "Default Title";
    let message = "Default Message";

    if (event.data) {
        try {
            const data = event.data.json();
            title = data.title;
            message = data.message;
        } catch (e) {
            console.log("Payload was not JSON, using raw text");
            message = event.data.text();
        }
    }

    event.waitUntil(
        self.registration.showNotification(title, {
            body: message,
            icon: "https://via.placeholder.com/128"
        })
    );
});
