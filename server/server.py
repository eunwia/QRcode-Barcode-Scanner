"""
Session 1-B - QR Code Scanner Backend (Python)

What it does:
    1. Listens on port 2025 for HTTP POST requests from the Android app.
    2. Receives the scanned text from the mobile device.
    3. Waits 3000 ms (as specified), then types the text into whatever window/input field is currently focused on the PC.
    4. Displays all scanned results in a web view at http://<ip>:2025/

To Run the Program:
If by initial running, you need to run this command in the cli:
    pip install flask pyautogui
    py server.py
"""

import time
import threading
import pyautogui
from flask import Flask, request, jsonify, render_template
from datetime import datetime

# Tell Flask to look for templates in the same folder as server.py
app = Flask(__name__, template_folder=".")

PORT = 2025
DELAY_MS = 3000   # 3-second delay before typing (as per spec)

# In-memory list of scanned results
scan_history = []


def type_after_delay(text: str, delay_ms: int):
    """Wait delay_ms milliseconds, then type text into the active window."""
    time.sleep(delay_ms / 1000)
    pyautogui.typewrite(text, interval=0.03)
    print(f"[typed] {text!r}")

# GET / Web View
@app.route("/", methods=["GET"])
def web_view():
    """Displays all scanned results using courser.html template."""
    return render_template("courser.html", scan_history=scan_history)


# POST /scan
@app.route("/scan", methods=["POST"])
def receive_scan():
    """
    Android app sends:
        POST /scan
        Content-Type: application/json
        { "text": "<scanned value>" }
    """
    data = request.get_json(silent=True)

    if not data or "text" not in data:
        return jsonify({"error": "Missing 'text' field in JSON body."}), 400

    scanned_text = str(data["text"]).strip()
    if not scanned_text:
        return jsonify({"error": "Scanned text is empty."}), 400

    # Save to the history
    scan_history.append({
        "text": scanned_text,
        "time": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    })
    
    print(f"[received] {scanned_text!r}  →  typing in {DELAY_MS}ms ...")

    # Fire and forget in a background thread
    thread = threading.Thread(
        target=type_after_delay,
        args=(scanned_text, DELAY_MS),
        daemon=True
    )
    thread.start()

    return jsonify({
        "success": True,
        "message": f"Will type result in {DELAY_MS}ms.",
        "received": scanned_text
    }), 200


# GET /ping
@app.route("/ping", methods=["GET"])
def ping():
    return jsonify({"status": "ok", "port": PORT}), 200

# GET /about
@app.route("/about", methods=["GET"])
def about():
    return jsonify({
        "title": "WorldSkills ASEAN Manila 2025",
        "description": (
            "World Skills ASEAN Manila 2025 is starting on 26 Aug 2025 "
            "to 31 Aug 2025 with 11 countries."
        )
    }), 200

if __name__ == "__main__":
    print(f"[server] Listening on port {PORT} ...")
    print(f"[server] Android should POST to  http://<your-pc-ip>:{PORT}/scan")
    print(f"[server] Emulator loopback:       http://10.0.2.2:{PORT}/scan")
    print(f"[server] Web view:                http://<your-pc-ip>:{PORT}/")
    app.run(host="0.0.0.0", port=PORT, debug=False)
