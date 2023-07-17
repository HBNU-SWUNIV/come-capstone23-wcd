const getUserInfo = (loginId, password) => {
    const reqData = {
        'loginId': loginId,
        'password': password
    }

    return {
        'data': {
            'loginId': reqData.loginId,
            'user_token': 'user_test_token',
            'user_role': 'ADM'
        }
    }
}

export default {
    async doLogin(loginId, password) {
        try {
            const getUserInfoPromise = getUserInfo(loginId, password)
            const [userInfoResponse] = await Promise.all([getUserInfoPromise])
            if (userInfoResponse.data.length === 0) {
                return 'notFound'
            } else {
                localStorage.setItem('user_token', userInfoResponse.data.user_token)
                localStorage.setItem('user_role', userInfoResponse.data.user_role)
                return userInfoResponse
            }
        } catch (err) {
            console.error(err)
        }
    }
}