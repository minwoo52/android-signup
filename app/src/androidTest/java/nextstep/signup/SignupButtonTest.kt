package nextstep.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import nextstep.signup.ui.component.SignupButton
import org.junit.Rule
import org.junit.Test


class SignupButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setSignupButtonTest(
        buttonText: String,
        onClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            SignupButton(
                text = buttonText,
                onClick = onClick
            )
        }
    }

    @Test
    fun signUpButton_ShowsText_WhenDisplayed() {
        // given
        val buttonText = "Sign Up"
        setSignupButtonTest(buttonText)

        // then
        composeTestRule
            .onNodeWithText(buttonText)
            .assertExists()
    }

    @Test
    fun signUpButton_TriggersOnClick_WhenClicked() {
        // Given
        val buttonText = "Sign Up"
        var clicked = false
        setSignupButtonTest(buttonText) { clicked = true }

        // Then
        composeTestRule
            .onNodeWithText(buttonText)
            .performClick()

        assertTrue(clicked)
    }
}