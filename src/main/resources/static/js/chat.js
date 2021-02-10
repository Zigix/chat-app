'use strict';

var messageInput = document.querySelector('#message-input');
var messageList = document.querySelector('#message-list');
var activeUsersList = document.querySelector('.active-users-list');

var username = null;
var stompClient = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {
    username = document.getElementById('name').value.trim();

    console.log(username);

    if(username) {
        var socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected);
    }
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);

    var joinMessage = JSON.stringify({
        sender: username,
        type: 'JOIN'
    });

    stompClient.send('/app/chat.register', {}, joinMessage);
}

function sendMessage() {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = JSON.stringify({
            sender: username,
            content: messageContent,
            type: 'CHAT'
        });
    }

    stompClient.send('/app/chat.send', {}, chatMessage);
    messageInput.value = '';
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' has joined!';
    }
    else if(message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' has left!';
    }
    else if(message.type === 'CHAT') {
        messageElement.classList.add('user-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var userElement = document.createElement('span');
        var userNameText = document.createTextNode(message.sender);
        userElement.appendChild(userNameText);

        messageElement.appendChild(userElement);
    }
    else if(message.type === 'UPDATE') {
        activeUsersList.innerHTML = '';
        for(let activeUser of message.activeUsers) {
            var activeUserElement = document.createElement('li');
            var activeUserAvatarElement = document.createElement('i');
            var activeUsernameElement = document.createElement('span');

            var activeUserAvatarText = document.createTextNode(activeUser.charAt(0));
            activeUserAvatarElement.appendChild(activeUserAvatarText);
            activeUserAvatarElement.style['background'] = getAvatarColor(activeUser);

            var activeUsernameText = document.createTextNode(activeUser);
            activeUsernameElement.appendChild(activeUsernameText);

            activeUserElement.appendChild(activeUserAvatarElement);
            activeUserElement.appendChild(activeUsernameElement);

            activeUsersList.appendChild(activeUserElement);
        }
    }

    if(message.type !== 'UPDATE') {
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);

        messageList.appendChild(messageElement);
    }
}

function getAvatarColor(name) {
    var index = hashCode(name) % 8;
    return colors[index];
}

function hashCode(str) {
    var hash = 0, i, chr;
    for (i = 0; i < str.length; i++) {
        chr   = str.charCodeAt(i);
        hash  = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}