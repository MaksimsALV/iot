document.getElementById("refreshBtn").addEventListener("click", async () => {
    const r = await fetch("/api/status");
    if (!r.ok) return;

    const data = await r.json();

    document.getElementById("led").textContent = data.actuators.ledIsOn ? "ON" : "OFF";
    document.getElementById("buzzer").textContent = data.actuators.buzzerIsOn ? "ON" : "OFF";
    document.getElementById("motor").textContent = data.actuators.motorIsOn ? "ON" : "OFF";

    document.getElementById("temperature").textContent = data.sensors.temperature ?? "";
    document.getElementById("humidity").textContent = data.sensors.humidity ?? "";
    document.getElementById("motion").textContent = data.sensors.lastMotionDetectedAt ?? "";
    document.getElementById("sound").textContent = data.sensors.lastSoundDetectedAt ?? "";
});