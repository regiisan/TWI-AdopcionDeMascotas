const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/spring/wschat'
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

    const container = document.createElement("div");
    container.className = tipo === "enviado" ? "d-flex justify-content-end mb-2" : "d-flex justify-content-start mb-2";

    const msgBubble = document.createElement("div");
    msgBubble.className = "p-2 rounded-3";
    msgBubble.style.maxWidth = "75%";
    msgBubble.style.backgroundColor = tipo === "enviado" ? "#5D3A9B" : "#d3d3d3";
    msgBubble.style.color = tipo === "enviado" ? "white" : "black";

    msgBubble.innerHTML = `<strong>${nombre}:</strong> ${texto}`;

    container.appendChild(msgBubble);
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
