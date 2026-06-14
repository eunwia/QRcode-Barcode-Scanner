<div>

# QR Code / Barcode Scanner with Web Server Integration

![Scanner](https://img.shields.io/badge/QR%20%2F%20Barcode-Scanner-1a8a8a?style=for-the-badge)
![Web Server](https://img.shields.io/badge/Web-Server%20Integration-2aabab?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-4dcfcf?style=for-the-badge)

*A system for scanning text from QR codes or barcodes using a smartphone and sending it to the cursor (server) on a computer.*

</div>

---

## 📱 Mobile Interfaces

<div>
<table border="0" style="border: none; border-collapse: collapse;">
  <tr>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-home.png" alt="Home Screen" width="180" height="320" style="border-radius: 10px; object-fit: cover;" />
      <br/>
      <sub>🏠 Home Screen</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-scan.png" alt="Scanner" width="180" height="320" style="border-radius: 10px; object-fit: cover;" />
      <br/>
      <sub>📷 Scanner</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-ip.png" alt="IP Address Setup" width="180" height="320" style="border-radius: 10px; object-fit: cover;" />
      <br/>
      <sub>🌐 IP Address Setup</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-port.png" alt="Port Setup" width="180" height="320" style="border-radius: 10px; object-fit: cover;" />
      <br/>
      <sub>🔌 Port Setup</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-about.png" alt="About" width="180" height="320" style="border-radius: 10px; object-fit: cover;" />
      <br/>
      <sub>ℹ️ About</sub>
    </td>
  </tr>
  <tr>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Homepage with buttons for scanner, port, IP address, and about.</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Scans QR/Barcode and sends result to web cursor with a 3000ms delay.</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Set the IP address to connect to the web cursor.</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Default port number is <code>2025</code>.</sub>
    </td>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Shows the description of the competition.</sub>
    </td>
  </tr>
</table>
</div>

---

## 🖥️ Server Side

<div>
<table border="0" style="border: none; border-collapse: collapse;">
  <tr>
    <td align="center" style="border: none; padding: 8px;">
      <img src="readme/qr-web.PNG" alt="Web Server" width="600" style="border-radius: 10px;" />
      <br/>
      <sub>🌐 Web Cursor — API Side</sub>
    </td>
  </tr>
  <tr>
    <td align="center" style="border: none; padding: 8px;">
      <sub>Waits for a connection from the mobile. Receives and displays the scanned QR Code / Barcode text.</sub>
    </td>
  </tr>
</table>
</div>

---

## 🛠️ Tech Stack

<div>

![Java](https://img.shields.io/badge/Java-1a8a8a?style=for-the-badge&logo=openjdk&logoColor=ffffff)
![HTML5](https://img.shields.io/badge/HTML5-2aabab?style=for-the-badge&logo=html5&logoColor=ffffff)
![Python](https://img.shields.io/badge/Python-4dcfcf?style=for-the-badge&logo=python&logoColor=ffffff)

</div>

---

## ⚙️ Key Features

**🏠 Clean Home Interface**
> Homepage consists of quick-access buttons for the scanner, port configuration, IP address setup, and about section.

**📷 QR / Barcode Scanner**
> Scans QR codes and barcodes via smartphone camera and sends the result to the web cursor over IP and port connection with a 3000ms transmission delay.

**🌐 IP Address Configuration**
> Manually set the target IP address to establish connection with the web cursor server.

**🔌 Port Configuration**
> Configurable port number with a default value of `2025`.

**🖥️ Web Cursor (Server Side)**
> API endpoint that listens for incoming mobile connections and displays the scanned QR Code / Barcode text in real time.

---

## 🚀 How to Run

**1. Activate the server**
> Navigate to: `QRcode-Barcode-Scanner → server`
```
python server.py
```
> Copy the running address shown in the terminal.

**2. Get your IP Address**
```
ipconfig
```
> Copy the **IPv4 Address** and input it in the mobile app under the IP Address section.

**3. Open Android Studio**
> Run the application on an emulator or physical device.

**4. Enjoy! 🎉**

---

## 🔑 Connection Details

| Setting | Value |
|---|---|
| Default Port | `2025` |
| Server Command | `python server.py` |
| IP Source | `ipconfig` → IPv4 Address |

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=1a8a8a&height=80&section=footer"/>
</div>
