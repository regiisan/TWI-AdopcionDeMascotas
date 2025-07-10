const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/tallerwebi-base-1.0-SNAPSHOT/wschat'
});

stompClient.debug = function(str) {
  console.log(str)
};

stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);

  stompClient.subscribe('/topic/messages', (m) => {
    const data = JSON.parse(m.body);
    const texto = data.texto;
    const emisorId = data.emisorId;
    const nombre = data.nombreUsuario;

    const usuarioId = document.getElementById("usuarioId").value;
    const tipo = emisorId == usuarioId ? "enviado" : "recibido";

    // Nombre arriba
    const userLabel = document.createElement("div");
    userLabel.textContent = nombre;
    userLabel.style.fontSize = "0.85rem";
    userLabel.style.fontWeight = "bold";
    userLabel.style.color = "#888";
    userLabel.style.marginBottom = "2px";
    userLabel.style.marginLeft = tipo === "enviado" ? "auto" : "0";

    // Burbuja de mensaje
    const msgBubble = document.createElement("div");
    msgBubble.className = "p-2 rounded-3";
    msgBubble.style.maxWidth = "75%";
    msgBubble.style.minWidth = "fit-content";
    msgBubble.style.backgroundColor = tipo === "enviado" ? "#5D3A9B" : "#d3d3d3";
    msgBubble.style.color = tipo === "enviado" ? "white" : "black";
    msgBubble.style.wordBreak = "break-word";
    msgBubble.style.whiteSpace = "pre-wrap";
    msgBubble.textContent = texto;

    // Agrupamos
    const contentWrapper = document.createElement("div");
    contentWrapper.className = "d-flex flex-column";
    contentWrapper.appendChild(userLabel);
    contentWrapper.appendChild(msgBubble);

    const container = document.createElement("div");
    container.className = tipo === "enviado" ? "d-flex justify-content-end mb-2" : "d-flex justify-content-start mb-2";
    container.appendChild(contentWrapper);

    const chatMessages = document.getElementById("chat-messages");
    chatMessages.appendChild(container);
    chatMessages.scrollTop = chatMessages.scrollHeight;
  });

};

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

stompClient.activate();

function sendMessage(){

  let input = document.getElementById("message");
  const message = input.value.trim();
  if (!message) return;

  const emisorId = document.getElementById("usuarioId").value;
  const nombreUsuario = document.getElementById("usuarioNombre").value;

  stompClient.publish({
    destination: "/app/chat",
    body: JSON.stringify({
      message: message,
      emisorId: emisorId,
      nombreUsuario: nombreUsuario
    })
  });

  input.value = "";
}
