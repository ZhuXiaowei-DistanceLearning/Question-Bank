//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zxw.netty.demo.handler.ws.xs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public final class WebSocketServerIndexPage {
    private static final String NEWLINE = "\r\n";

    public static ByteBuf getContent(String webSocketLocation) {
        return Unpooled.copiedBuffer("<html><head><title>Web Socket Test</title></head>\r\n<body>\r\n<script type=\"text/javascript\">\r\nvar socket;\r\nif (!window.WebSocket) {\r\n  window.WebSocket = window.MozWebSocket;\r\n}\r\nif (window.WebSocket) {\r\n  socket = new WebSocket(\"" + webSocketLocation + "\");" + "\r\n" + "  socket.onmessage = function(event) {" + "\r\n" + "    var ta = document.getElementById('responseText');" + "\r\n" + "    ta.value = ta.value + '\\n' + event.data" + "\r\n" + "  };" + "\r\n" + "  socket.onopen = function(event) {" + "\r\n" + "    var ta = document.getElementById('responseText');" + "\r\n" + "    ta.value = \"Web Socket opened!\";" + "\r\n" + "  };" + "\r\n" + "  socket.onclose = function(event) {" + "\r\n" + "    var ta = document.getElementById('responseText');" + "\r\n" + "    ta.value = ta.value + \"Web Socket closed\"; " + "\r\n" + "  };" + "\r\n" + "} else {" + "\r\n" + "  alert(\"Your browser does not support Web Socket.\");" + "\r\n" + '}' + "\r\n" + "\r\n" + "function send(message) {" + "\r\n" + "  if (!window.WebSocket) { return; }" + "\r\n" + "  if (socket.readyState == WebSocket.OPEN) {" + "\r\n" + "    socket.send(message);" + "\r\n" + "  } else {" + "\r\n" + "    alert(\"The socket is not open.\");" + "\r\n" + "  }" + "\r\n" + '}' + "\r\n" + "</script>" + "\r\n" + "<form onsubmit=\"return false;\">" + "\r\n" + "<input type=\"text\" name=\"message\" value=\"Hello, World!\"/><input type=\"button\" value=\"Send Web Socket Data\"" + "\r\n" + "       onclick=\"send(this.form.message.value)\" />" + "\r\n" + "<h3>Output</h3>" + "\r\n" + "<textarea id=\"responseText\" style=\"width:500px;height:300px;\"></textarea>" + "\r\n" + "</form>" + "\r\n" + "</body>" + "\r\n" + "</html>" + "\r\n", CharsetUtil.US_ASCII);
    }

    private WebSocketServerIndexPage() {
    }
}
