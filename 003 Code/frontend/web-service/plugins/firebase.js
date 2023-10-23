import firebase from "firebase/app";
import "firebase/messaging";

let firebaseConfig = {
  apiKey: "AIzaSyDtuhqyI_bNOj-8gZEtzfITlW_LJx--1pk",
  authDomain: "fcmpractice-9a12e.firebaseapp.com",
  projectId: "fcmpractice-9a12e",
  storageBucket: "fcmpractice-9a12e.appspot.com",
  messagingSenderId: "647377856840",
  appId: "1:647377856840:web:f226ba8af8de032d93ab7d",
  measurementId: "G-XYYR3W9FHE",
};

if (!firebase.apps.length) {
  firebase.initializeApp(firebaseConfig);

  //Service Worker 등록
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker
      .register("/firebase-messaging-sw.js")
      .then((registration) => {
        const messaging = firebase.messaging();
        messaging.useServiceWorker(registration);

        console.log("서비스워커 등록 완료");

        // Service Worker 등록 후에 푸시 토큰을 요청
        messaging
          .getToken({ vapidKey: "BN4UXj5_xn7lEegLHRDDlVlNQPbHV26-I9HCz_RR-ucOJAw_LpP78EmEmqt9DVhZiO5SxtBP0gH1a0NrjjtC-xw" })
          .then((tokenValue) => {
            console.log("푸시토큰 발급완료:", tokenValue);
            localStorage.setItem("push_token", tokenValue);
          })
          .catch((error) => {
            console.error("푸시 토큰 못받아옴", error);
          });
      })
      .catch((error) => {
        console.error("Service Worker 등록 실패:", error);
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
