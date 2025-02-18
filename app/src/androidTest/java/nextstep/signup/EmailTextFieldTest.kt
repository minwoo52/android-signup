package nextstep.signup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import nextstep.signup.ui.signin.EmailTextField
import nextstep.signup.ui.signin.SignupValidator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EmailTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            var email by remember { mutableStateOf("") }
            val emailValidation by remember {
                derivedStateOf { SignupValidator.validateEmail(email) }
            }

            EmailTextField(
                label = EMAIL_LABEL,
                value = email,
                onValueChange = { email = it },
                validationResult = emailValidation,
            )
        }
    }

    @Test
    fun 이메일_형식이_올바르면_에러메시지_노출되지_않는다() {
        // given
        val text = "compose@gmail.com"

        // when
        composeTestRule
            .onNodeWithText(EMAIL_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(EMAIL_INVALID_FORMAT_ERROR)
            .assertDoesNotExist()
    }

    @Test
    fun 이메일_형식이_올바르지_않으면_에러메시지_노출된다() {
        // given
        val text = "compose#gmail.com"

        // when
        composeTestRule
            .onNodeWithText(EMAIL_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(EMAIL_INVALID_FORMAT_ERROR)
            .assertExists()
    }

    companion object {
        // Label
        private const val EMAIL_LABEL = "Email"

        // ErrorMessage
        private const val EMAIL_INVALID_FORMAT_ERROR = "이메일 형식이 올바르지 않습니다."
    }
}