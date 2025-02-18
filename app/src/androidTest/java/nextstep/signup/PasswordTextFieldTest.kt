package nextstep.signup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import nextstep.signup.ui.signin.PasswordTextField
import nextstep.signup.ui.signin.SignupValidator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PasswordTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            var password by remember { mutableStateOf("") }
            val passwordValidation by remember {
                derivedStateOf { SignupValidator.validatePassword(password) }
            }

            PasswordTextField(
                label = PASSWORD_LABEL,
                value = password,
                onValueChange = { password = it },
                validationResult = passwordValidation,
            )
        }
    }

    @Test
    fun 비밀번호는_8에서_16자_이어야_한다() {
        // given
        val text = "aaaa1234"

        // when
        composeTestRule
            .onNodeWithText(PASSWORD_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(PASSWORD_LENGTH_ERROR)
            .assertDoesNotExist()
    }

    @Test
    fun 비밀번호가_8자가_안되면_에러메시지_노출된다() {
        // given
        val text = "aaaa123"

        // when
        composeTestRule
            .onNodeWithText(PASSWORD_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(PASSWORD_LENGTH_ERROR)
            .assertExists()
    }

    @Test
    fun 비밀번호가_16자를_넘으면_에러메시지_노출된다() {
        // given
        val text = "aaaabbbbccccdddd0"

        // when
        composeTestRule
            .onNodeWithText(PASSWORD_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(PASSWORD_LENGTH_ERROR)
            .assertExists()
    }

    @Test
    fun 비밀번호는_영문과_숫자가_포함되지_않으면_에러메시지_노출된다() {
        // given
        val text = "abcdabcd#"

        // when
        composeTestRule
            .onNodeWithText(PASSWORD_LABEL)
            .performTextInput(text)

        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onNodeWithText(PASSWORD_INVALID_FORMAT_ERROR)
            .assertExists()
    }

    companion object {
        // Label
        private const val PASSWORD_LABEL = "Password"

        // ErrorMessage
        private const val PASSWORD_LENGTH_ERROR = "비밀번호는 8~16자여야 합니다."
        private const val PASSWORD_INVALID_FORMAT_ERROR = "비밀번호는 영문과 숫자를 포함해야 합니다."
    }
}