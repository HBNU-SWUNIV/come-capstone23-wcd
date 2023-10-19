import firebase from 'firebase/app';
import 'firebase/messaging';

let firebaseConfig = {
    apiKey: process.env.FIREBASE_API_KEY,
      authDomain: process.env.FIREBASE_AUTH_DOMAIN,
      projectId: process.env.FIREBASE_PROJECT_ID,
      storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
      messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
      appId: process.env.FIREBASE_APP_ID,
      measurementId: process.env.FIREBASE_MEASUREMENT_ID,
};

if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);

    //Service Worker 등록
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('/firebase-messaging-sw.js')
            .then((registration) => {
                const messaging = firebase.messaging();
                messaging.useServiceWorker(registration);

                console.log("서비스워커 등록 완료")

                // Service Worker 등록 후에 푸시 토큰을 요청
                messaging.getToken({ vapidKey: process.env.FIREBASE_VAPID_KEY })
                    .then((tokenValue) => {
                        console.log('푸시토큰 발급완료:', tokenValue);
                        localStorage.setItem("push_token", tokenValue)
                        // this.$store.commit("setPushToken", tokenValue);
                        // console.log(this.$store.state.push_token)
                        // subscribeToPushNotifications(tokenValue);
                    }).catch(error => {
                        console.error('푸시 토큰 못받아옴', error);
                    });
            })
            .catch((error) => {
                console.error('Service Worker 등록 실패:', error);
            });
    }
}

// function subscribeToPushNotifications(token) {
//     const data = {
//         token,
//         topicList: [1] // 구독하려는 주제 리스트
//     };

//     fetch("http://211.115.222.246:5004/subscribe", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(data)
//     })
//         .then((response) => {
//             console.log("Push 알림 구독 완료");

//             const messageData = {
//                 clubName: "클럽이름",
//                 userName: "유저이름",
//                 chatMessage: "메시지내용",
//                 topic: 1,
//             }
//             fetch('http://211.115.222.246:5004/sendChatMessage', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 body: JSON.stringify(messageData)
//             })
//                 .then(response => {
//                     console.log('데이터 전송 성공:', response);
//                 })
//                 .catch(error => {
//                     console.error('데이터 전송 중 오류:', error);
//                 });
//         })
//         .catch((error) => {
//             console.error("Push 알림 구독 실패:", error);
//         });
// }