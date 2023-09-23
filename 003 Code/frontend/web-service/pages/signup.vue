<template>
  <div style="width: 600px; margin-top: 40px">
    <v-container>
      <v-card style="background-color: rgba(0, 0, 0, 0.9)">
        <v-card-title class="d-flex justify-center">
          <h1 style="padding: 20px">SIGN UP</h1>
        </v-card-title>
        <v-card-text>
          <v-form @submit.prevent="SignupSubmit" ref="form" lazy-validation>
            <v-text-field
              id="name"
              v-model="name"
              placeholder="이름"
              :rules="user_name_rule"
              @input="validateName"
            ></v-text-field>

            <div
              style="
                padding: 0;
                display: flex;
                flex-direction: row;
                align-items: center;
              "
            >
              <v-text-field
                id="email"
                v-model="email"
                placeholder="이메일"
                :rules="user_email_rule"
                style="padding-right: 20px"
                @input="validateEmail"
                :disabled="emailValid"
              >
              </v-text-field>
              <v-btn
                class="custom-button"
                v-show="!showResendButton"
                :disabled="!isEmailValid || emailValid"
                @click="openValidBox"
                >이메일 인증</v-btn
              >
              <v-btn
                class="custom-button"
                v-show="showResendButton"
                @click="resendVerificationCode"
                >재전송</v-btn
              >
            </div>
            <p v-show="emailValid" style="color: rgb(138, 255, 138)">
              인증이 완료되었습니다.
            </p>
            <p v-show="alreadyEmail" style="color: rgb(255, 110, 110)">
              이미 가입된 이메일입니다.
            </p>

            <div
              v-show="validBox"
              style="
                padding: 0;
                display: flex;
                flex-direction: row;
                align-items: center;
              "
            >
              <!-- 이메일 인증 번호 입력 창 -->
              <v-text-field
                v-model="verificationCode"
                placeholder="인증 번호 입력"
                label="인증 번호"
                style="padding-right: 20px; padding-top: 0"
              ></v-text-field>
              <v-btn class="custom-button" @click="verifyCode">인증 확인</v-btn>
            </div>
            <div v-show="timerVisible" style="padding: 0">
              <p>남은 시간: {{ minutes }} 분 {{ seconds }} 초</p>
            </div>
            <p v-show="codeIncorrect" style="color: red">
              인증코드가 일치하지 않습니다.
            </p>

            <v-text-field
              id="password"
              v-model="password"
              placeholder="비밀번호"
              :rules="user_password_rule"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword = !showPassword"
              :type="showPassword ? 'text' : 'password'"
              @input="validatePassword"
            ></v-text-field>

            <v-chip
              v-if="passwordStrength"
              :color="passwordStrengthColor"
              class="password-strength"
            >
              {{ passwordStrength }}
            </v-chip>

            <v-text-field
              id="pwdCheck"
              v-model="pwCheck"
              placeholder="비밀번호 확인"
              :rules="user_password_rule2"
              :append-icon="showPassword2 ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword2 = !showPassword2"
              :type="showPassword2 ? 'text' : 'password'"
              @input="validatePwCheck"
            ></v-text-field>

            <v-text-field
              id="phoneNumber"
              v-model="phoneNumber"
              placeholder="전화번호"
              type="tel"
              :rules="user_phonenumber_rule"
              @input="validatePhoneNumber"
            ></v-text-field>

            <!-- <v-text-field
              id="birthday"
              v-model="birthday"
              placeholder="생년월일"
              type="date"
              @input="validateBirthday"
            ></v-text-field> -->

            <v-row>
              <v-col cols="4">
                <!-- 년도 선택 목록 -->
                <v-select
                  v-model="selectedYear"
                  :items="years"
                  label="년도"
                  @input="validateYear"
                ></v-select>
              </v-col>
              <v-col cols="4">
                <!-- 월 선택 목록 -->
                <v-select
                  v-model="selectedMonth"
                  :items="months"
                  label="월"
                  @change="updateDays"
                  @input="validateMonth"
                ></v-select>
              </v-col>
              <v-col cols="4">
                <!-- 일 선택 목록 -->
                <v-select
                  v-model="selectedDay"
                  :items="days"
                  label="일"
                  @input="validateDay"
                ></v-select>
              </v-col>
            </v-row>

            <v-select
              id="gender"
              v-model="gender"
              :items="['MALE', 'FEMALE']"
              placeholder="성별"
              @input="validateGender"
            ></v-select>
            <v-btn to="/login" style="width:100%;">이미 회원이신가요?</v-btn>
            <v-btn
              :disabled="
                !(
                  nameValid &&
                  emailValid &&
                  passwordValid &&
                  pwCheckValid &&
                  phoneNumberValid &&
                  yearValid &&
                  monthValid &&
                  dayValid &&
                  genderValid
                )
              "
              class="mt-3"
              block
              type="submit"
            >
              회원가입
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-container>
  </div>
</template>

<script>
export default {
  layout(context) {
    return "signup";
  },
  data() {
    return {
      name: "",
      nameRegex: /^[가-힣]+$/,
      emailRegex: /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
      passwordRegex: /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).*$/,
      phoneNumberRegex: /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/,
      user_name_rule: [
        (v) => !!v || "이름은 필수 입력사항입니다.",
        (v) => !(v && v.length >= 30) || "이름은 30자 이상 입력할 수 없습니다.",
        (v) => this.nameRegex.test(v) || "이름에는 한글만 사용할 수 있습니다.",
      ],
      email: "",
      user_email_rule: [
        (v) => !!v || "이메일은 필수 입력사항입니다.",
        (v) =>
          this.emailRegex.test(v) || "이메일은 영문+숫자만 입력 가능합니다.",
        (v) =>
          (v && v.length >= 5 && v.length < 40) ||
          "이메일은 5글자 이상, 40글자 미만이어야 합니다.",
      ],
      password: "",
      pwCheck: "",
      user_password_rule: [
        (v) => !!v || "비밀번호는 필수 입력사항입니다.",
        (v) =>
          this.passwordRegex.test(v) ||
          "비밀번호는 영문자, 특수문자, 숫자조합으로 8자 이상, 20자 미만이어야 합니다.",
        (v) =>
          (v && v.length >= 8 && v.length < 20) ||
          "비밀번호는 8자 이상, 20자 미만이어야 합니다.",
      ],
      user_password_rule2: [
        (v) => !!v || "비밀번호 확인은 필수 입력사항입니다.",
        (v) => v === this.password || "패스워드가 일치하지 않습니다.",
      ],
      phoneNumber: "",
      user_phonenumber_rule: [
        (v) => !!v || "전화번호는 필수 입력사항입니다.",
        (v) =>
          this.phoneNumberRegex.test(v) || "전화번호 형식에 맞지 않습니다.",
      ],
      birthday: "",
      gender: "",
      showPassword: false,
      showPassword2: false,
      isEmailValid: false,
      validBox: false,
      timerVisible: false, // 타이머 표시 여부
      verificationCode: "", // 입력한 인증 번호
      minutes: 3, // 타이머 분
      seconds: 0, // 타이머 초
      showResendButton: false,
      timerId: null,
      emailValid: false,
      nameValid: false,
      passwordValid: false,
      pwCheckValid: false,
      phoneNumberValid: false,
      yearValid: false,
      monthValid: false,
      dayValid: false,
      genderValid: false,
      passwordStrength: "",
      codeIncorrect: false,
      years: [], // 년도 목록
      months: [], // 월 목록
      days: [], // 일 목록
      selectedYear: null, // 선택한 년도
      selectedMonth: null, // 선택한 월
      selectedDay: null, // 선택한 일
      alreadyEmail: false,
    };
  },
  mounted() {
    // 년도, 월, 일 목록을 초기화하는 로직
    this.initDateLists();
  },
  methods: {
    async SignupSubmit() {
      // JSON 형식의 데이터
      let SignupData = {
        email: this.email,
        password: this.password,
        name: this.name,
        phoneNumber: this.phoneNumber,
        birthday:
          this.selectedYear +
          "-" +
          this.selectedMonth.toString().padStart(2, "0") +
          "-" +
          this.selectedDay.toString().padStart(2, "0"), // 년도, 월, 일을 조합하여 생년월일 생성
        gender: this.gender,
      };

      try {
        if (this.name === "") {
          alert("이름을 입력하세요.");
          return;
        }
        if (this.email === "") {
          alert("이메일을 입력하세요.");
          return;
        }
        if (this.password === "") {
          alert("비밀번호를 입력하세요.");
          return;
        }
        if (this.password !== this.pwCheck) {
          alert("비밀번호 확인이 일치하지 않습니다.");
          return;
        }
        if (this.phoneNumber === "") {
          alert("전화번호를 입력하세요.");
          return;
        }
        if (this.selectedYear === "") {
          alert("년도를 선택하세요.");
          return;
        }
        if (this.selectedMonth === "") {
          alert("월을 선택하세요.");
          return;
        }
        if (this.selectedDay === "") {
          alert("일을 선택하세요.");
          return;
        }
        if (this.gender === "") {
          alert("성별을 선택하세요.");
          return;
        }

        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
          },
        };

        await this.$axios
          .post("/user-service/signup", JSON.stringify(SignupData), config)
          .then((res) => {
            console.log(res);
            alert("회원가입 되었습니다.");
            this.$router.push("/login");
          });
      } catch (err) {
        console.log(err);
      }
    },
    validateName() {
      if (this.name === "") {
        this.nameValid = false;
        return;
      }

      if (this.nameRegex.test(this.name) && this.name.length < 30) {
        this.nameValid = true;
      } else {
        this.nameValid = false;
      }
    },
    validateEmail() {
      this.alreadyEmail = false;
      
      if (this.email === "") {
        this.isEmailValid = false;
        return;
      }

      if (
        this.emailRegex.test(this.email) &&
        this.email.length >= 5 &&
        this.email.length < 40
      ) {
        this.isEmailValid = true;
      } else {
        this.isEmailValid = false;
      }
    },
    validatePassword() {
      if (this.password === "") {
        this.passwordValid = false;
        return;
      }

      if (
        this.passwordRegex.test(this.password) &&
        this.password.length >= 8 &&
        this.password.length < 20
      ) {
        this.passwordValid = true;
      } else {
        this.passwordValid = false;
      }

      this.calculatePasswordStrength();
    },
    validatePwCheck() {
      if (this.password !== this.pwCheck) {
        this.pwCheckValid = false;
        return;
      } else {
        this.pwCheckValid = true;
      }
    },
    validatePhoneNumber() {
      if (this.phoneNumber === "") {
        this.phoneNumberValid = false;
        return;
      }

      if (this.phoneNumberRegex.test(this.phoneNumber)) {
        this.phoneNumberValid = true;
      } else {
        this.phoneNumberValid = false;
      }
    },
    validateYear() {
      if (this.selectedYear === "") {
        this.yearValid = false;
        return;
      } else {
        this.yearValid = true;
      }
    },
    validateMonth() {
      if (this.selectedMonth === "") {
        this.monthValid = false;
        return;
      } else {
        this.monthValid = true;
      }
    },
    validateDay() {
      if (this.selectedDay === "") {
        this.dayValid = false;
        return;
      } else {
        this.dayValid = true;
      }
    },
    validateGender() {
      if (this.gender === "") {
        this.genderValid = false;
        return;
      } else {
        this.genderValid = true;
      }
    },
    async sendValidCode() {
      let EmailData = {
        email: this.email,
      };
      try {
        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
          },
        };
        await this.$axios
          .post("/user-service/mail/send", JSON.stringify(EmailData), config)
          .then((res) => {
            console.log(res);
            alert("인증코드가 전송되었습니다.");
          });
      } catch (err) {
        if (err.response && err.response.status === 409) {
          // 에러 응답이 409인 경우
          console.log("이미 가입된 이메일입니다.");

          // 에러 발생 시 초기화
          this.validBox = false;
          this.timerVisible = false;
          this.showResendButton = false;
          this.stopTimer();
          this.isEmailValid = false;
          this.emailValid = false; // 인증 상태를 다시 false로 설정
          this.codeIncorrect = false; // 인증코드 일치 여부 초기화
          this.verificationCode = ""; // 입력한 인증 코드 초기화
          this.alreadyEmail = true;
        } else {
          console.log(err);
        }
      }
    },
    openValidBox() {
      this.validBox = true;
      this.startTimer();
      this.sendValidCode();
    },
    startTimer() {
      this.timerVisible = true;
      const totalSeconds = this.minutes * 60 + this.seconds;

      let remainingSeconds = totalSeconds;

      this.timerId = setInterval(() => {
        // 타이머 ID를 저장
        remainingSeconds--;

        if (remainingSeconds <= 0) {
          clearInterval(this.timerId); // 타이머 중지
          this.timerVisible = false;

          // 타이머 종료 시 timerExpired 메서드 호출
          this.timerExpired();
        } else {
          this.minutes = Math.floor(remainingSeconds / 60);
          this.seconds = remainingSeconds % 60;
        }
      }, 1000);
    },
    resendVerificationCode() {
      this.minutes = 3; // 재설정할 분
      this.seconds = 0; // 재설정할 초
      // 여기에서 타이머를 다시 시작하도록 구현
      this.startTimer();

      // 여기에서 이메일을 다시 보내는 작업을 수행하도록 구현
      // 예: 서버 API를 호출하여 이메일 재전송 요청

      // 재전송 버튼을 다시 숨깁니다.
      this.showResendButton = false;
    },
    timerExpired() {
      // 타이머가 종료되면 재전송 버튼을 표시합니다.
      this.showResendButton = true;
    },
    stopTimer() {
      if (this.timerId) {
        clearInterval(this.timerId); // 타이머 중지
        this.timerId = null; // 타이머 ID 초기화
      }
    },
    async verifyCode() {
      let VerificationData = {
        email: this.email,
        code: this.verificationCode,
      };
      try {
        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
          },
        };
        await this.$axios
          .post(
            "/user-service/mail/check",
            JSON.stringify(VerificationData),
            config
          )
          .then((res) => {
            console.log(res.status);
            if (res.status == 200) {
              this.validBox = false;
              this.timerVisible = false;
              this.showResendButton = false;
              this.stopTimer();
              this.emailValid = true;
            }
          });
      } catch (err) {
        console.log(err);
      }
    },
    calculatePasswordStrength() {
      const password = this.password;
      let strength = "";

      if (password.length < 8) {
        strength = "약함";
      } else if (password.length < 12) {
        strength = "보통";
      } else {
        strength = "강함";
      }

      // 문자 다양성과 특수 문자 포함 여부를 고려하여 등급 업데이트
      if (
        /[a-z]/.test(password) &&
        /[A-Z]/.test(password) &&
        /[0-9]/.test(password) &&
        /[!@#$%^&*]/.test(password)
      ) {
        strength = "강함";
      }

      this.passwordStrength = strength;
    },
    initDateLists() {
      // 년도 목록 초기화 (현재 년도부터 1900년까지)
      const currentYear = new Date().getFullYear();
      for (let i = currentYear; i >= 1900; i--) {
        this.years.push(i);
      }

      // 월 목록 초기화
      for (let i = 1; i <= 12; i++) {
        this.months.push(i);
      }

      // 일 목록 초기화
      this.updateDays();
    },
    updateDays() {
      // 선택한 월에 따라 일 목록을 동적으로 업데이트
      const selectedMonth = this.selectedMonth;
      let daysInMonth = 31; // 기본적으로 31일로 초기화

      // 4로 나누어 떨어지고 100으로 나누어 떨어지지 않거나 400으로 나누어 떨어지면 윤년
      if (
        (this.selectedYear % 4 === 0 && this.selectedYear % 100 !== 0) ||
        this.selectedYear % 400 === 0
      ) {
        // 2월을 29일로 설정 (윤년)
        if (selectedMonth === 2) {
          daysInMonth = 29;
        }
      } else {
        // 2월을 28일로 설정 (윤년이 아닌 경우)
        if (selectedMonth === 2) {
          daysInMonth = 28;
        }
      }

      this.days = Array.from({ length: daysInMonth }, (_, i) => i + 1);
    },
  },
  computed: {
    passwordStrengthColor() {
      // 등급에 따른 색상 결정
      switch (this.passwordStrength) {
        case "약함":
          return "error--text"; // 빨간색 텍스트
        case "보통":
          return "warning--text"; // 주황색 텍스트
        case "강함":
          return "success--text"; // 녹색 텍스트
        default:
          return ""; // 기본 색상
      }
    },
  },
};
</script>

<style scoped>
.custom-button {
  width: 110px;
}
.password-strength {
  margin-top: 10px;
  font-weight: bold;
}
</style>