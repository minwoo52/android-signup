package nextstep.signup

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import nextstep.signup.ui.signin.SignupScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SignupScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SignupScreen()
        }
    }

    @Test
    fun 모든_필드가_유효성검사를_통과하였다면_signup_버튼이_활성화된다() {
        // given
        composeTestRule.onNodeWithText(USERNAME_LABEL).performTextInput("abcd")
        composeTestRule.onNodeWithText(EMAIL_LABEL).performTextInput("abc@abc.com")
        composeTestRule.onNodeWithText(PASSWORD_LABEL).performTextInput("abcd1234")
        composeTestRule.onNodeWithText(PASSWORD_CONFIRM_LABEL).performTextInput("abcd1234")

        // when
        composeTestRule.waitForIdle()

        // then
        composeTestRule.onNodeWithText(SIGNUP_BUTTON).assertIsEnabled()
    }

    @Test
    fun 모든_필드가_유효성검사를_통과하였을때_signup_버튼을_클릭하면_snackbar에_회원가입_완료_메시지가_노출된다() {

        val expectedValue = "회원가입 완료"

        // given
        composeTestRule.onNodeWithText(USERNAME_LABEL).performTextInput("abcd")
        composeTestRule.onNodeWithText(EMAIL_LABEL).performTextInput("abc@abc.com")
        composeTestRule.onNodeWithText(PASSWORD_LABEL).performTextInput("abcd1234")
        composeTestRule.onNodeWithText(PASSWORD_CONFIRM_LABEL).performTextInput("abcd1234")

        // when
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(SIGNUP_BUTTON).performClick()

        // then
        composeTestRule.onNodeWithText(expectedValue).assertExists()
    }

    companion object {
        private const val USERNAME_LABEL = "Username"
        private const val EMAIL_LABEL = "Email"
        private const val PASSWORD_LABEL = "Password"
        private const val PASSWORD_CONFIRM_LABEL = "Password Confirm"
        private const val SIGNUP_BUTTON = "Sign Up"
    }
}