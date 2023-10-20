importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

// Firebase SDK를 초기화합니다.
const firebaseConfig = {
    apiKey: process.env.FIREBASE_API_KEY,
      authDomain: process.env.FIREBASE_AUTH_DOMAIN,
      projectId: process.env.FIREBASE_PROJECT_ID,
      storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
      messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
      appId: process.env.FIREBASE_APP_ID,
      measurementId: process.env.FIREBASE_MEASUREMENT_ID,
};

// if (!firebase.apps.length) {
//     firebase.initializeApp(firebaseConfig);
// }
firebase.initializeApp(firebaseConfig);

// Firebase Cloud Messaging Service Worker 로직을 구현합니다.
const messaging = firebase.messaging();

// 이어서 FCM 관련 로직을 Service Worker에 추가할 수 있습니다.
// 예: 알림 수신 및 표시, 백그라운드 메시지 처리 등.
self.addEventListener('activate', (event) => {
    event.waitUntil(
        // 비동기 작업을 수행하는 Promise 반환
        // 작업이 완료되면 다음과 같이 활성화 및 클라이언트 업데이트를 수행
        Promise.all([
            self.skipWaiting(), // 새 서비스 워커 활성화
            self.clients.claim(), // 클라이언트 업데이트
        ])
    );
});