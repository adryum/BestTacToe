import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.GrayLight
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.textNormal
import com.testdevlab.besttactoe.ui.theme.textSmall

@Composable
fun textInputColors(containerColor: Color) = TextFieldDefaults.colors(
    cursorColor = Orange,
    focusedTextColor = White,
    unfocusedTextColor = White,
    focusedLabelColor = GrayLight,
    unfocusedLabelColor = GrayLight,
    unfocusedPlaceholderColor = GrayLight,
    focusedPlaceholderColor = GrayLight,
    disabledPlaceholderColor = GrayLight,
    focusedContainerColor = containerColor,
    unfocusedContainerColor = containerColor,
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    selectionColors = TextSelectionColors(handleColor = Orange, backgroundColor = GrayDark),
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun TextInputWithIcon(
    modifier: Modifier,
    hint: String = "…",
    text: TextFieldValue = TextFieldValue(""),
    maxLines: Int = 1,
    singleLine: Boolean = true,
    showLabel: Boolean = true,
    textStyle: TextStyle = textNormal,
    hintStyle: TextStyle = textSmall,
    trailingIcon: @Composable () -> Unit = {},
    readOnly: Boolean = false,
    containerColor: Color = White,
    colors: TextFieldColors = textInputColors(containerColor),
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Int = 2,
    borderColor: Color = GrayLight,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChanged: (TextFieldValue) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val label: (@Composable () -> Unit)? = if (showLabel) {{
        Text(
            text = hint,
            style = hintStyle,
        )
    }} else null

    BasicTextField(
        modifier = modifier.border(width = borderWidth.dp, color = borderColor, shape = shape),
        value = text,
        readOnly = readOnly,
        maxLines = maxLines,
        singleLine = singleLine,
        textStyle = textStyle,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        cursorBrush = SolidColor(Orange),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = text.text,
                innerTextField = innerTextField,
                trailingIcon = { trailingIcon() },
                placeholder = label,
                shape = shape,
                singleLine = singleLine,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    )
}
