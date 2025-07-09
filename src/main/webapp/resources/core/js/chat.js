//const stompClient = new StompJs.Client({
//    brokerURL: 'ws://localhost:8080/tallerwebi-base-1.0-SNAPSHOT/wschat'
//});
//
//stompClient.debug = function(str) {
//    console.log(str)
//};
//
//stompClient.onConnect = (frame) => {
//    console.log('Connected: ' + frame);
//    stompClient.subscribe('/topic/messages', (m) => {
//        console.log(JSON.parse(m.body).content);
//        const messagesContainer = document.getElementById("chat-messages");
//        const newMessage = document.createElement("p")
//        newMessage.textContent = JSON.parse(m.body).content;
//        messagesContainer.appendChild(newMessage);
//    });
//};
//
//stompClient.onWebSocketError = (error) => {
//    console.error('Error with websocket', error);
//};
//
//stompClient.onStompError = (frame) => {
//    console.error('Broker reported error: ' + frame.headers['message']);
//    console.error('Additional details: ' + frame.body);
//};
//
//stompClient.activate();
//
//function sendMessage(){
//
//    let input = document.getElementById("message");
//    let message = input.value;
//
//    stompClient.publish({
//        destination: "/app/chat",
//        body: JSON.stringify({message: message})
//    });
//
//    input.value = "";
//}

const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/tallerwebi-base-1.0-SNAPSHOT/wschat'
});

stompClient.debug = str => console.log(str);

stompClient.onConnect = frame => {
  console.log('Connected:', frame);

  // Nos suscribimos a los mensajes entrantes
  stompClient.subscribe('/topic/messages', msg => {
    const { content } = JSON.parse(msg.body);
    addMessage(content, false);      // mensaje de otro usuario
  });
};

stompClient.onWebSocketError = err => console.error('WebSocket error', err);
stompClient.onStompError     = f   => console.error('STOMP error', f.headers.message);

stompClient.activate();

function addMessage(text, own) {
  const box = document.createElement('div');
  box.className = `d-flex ${own ? 'justify-content-end' : 'justify-content-start'} mb-2`;

  const bubble = document.createElement('div');
  bubble.className = 'p-2 rounded-3';
  bubble.style.maxWidth = '75%';
  bubble.textContent = text;

  if (own) {
    bubble.classList.add('text-white');
    bubble.style.backgroundColor = '#5D3A9B';
  } else {
    bubble.classList.add('bg-light', 'text-dark');
  }

  box.appendChild(bubble);
  const container = document.getElementById('chat-messages');
  container.appendChild(box);
  container.scrollTop = container.scrollHeight; // auto-scroll
}

function sendMessage() {
  const input = document.getElementById('message');
  const text  = input.value.trim();
  if (!text) return;

  stompClient.publish({
    destination: '/app/chat',
    body: JSON.stringify({ message: text })
  });

  addMessage(text, true);

  input.value = '';
}

document.getElementById('message').addEventListener('keydown', e => {
  if (e.key === 'Enter') {
    e.preventDefault();
    sendMessage();
  }
});


document.querySelector('.btn.btn-primary').addEventListener('click', sendMessage);
