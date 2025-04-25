import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.testdevlab.besttactoe.ui.components.Button
import com.testdevlab.besttactoe.ui.components.ButtonType
import com.testdevlab.besttactoe.ui.theme.Black
import com.testdevlab.besttactoe.ui.theme.Black35
import com.testdevlab.besttactoe.ui.theme.GrayDark
import com.testdevlab.besttactoe.ui.theme.GrayLight
import com.testdevlab.besttactoe.ui.theme.Orange
import com.testdevlab.besttactoe.ui.theme.OrangeList
import com.testdevlab.besttactoe.ui.theme.White
import com.testdevlab.besttactoe.ui.theme.getSportFontFamily
import com.testdevlab.besttactoe.ui.theme.ldp
import com.testdevlab.besttactoe.ui.theme.popInOut
import com.testdevlab.besttactoe.ui.theme.textCode
import com.testdevlab.besttactoe.ui.theme.textMedium
import com.testdevlab.besttactoe.ui.theme.textSmall
import com.testdevlab.besttactoe.ui.theme.textTitle

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
    textStyle: TextStyle = textMedium,
    fontFamily: FontFamily = FontFamily.Default,
    hintStyle: TextStyle = textSmall,
    trailingIcon: @Composable () -> Unit = {},
    readOnly: Boolean = false,
    containerColor: Color = White,
    colors: TextFieldColors = textInputColors(containerColor),
    shape: Shape = RoundedCornerShape(8.dp),
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
        modifier = modifier,
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

@Composable
fun CodeInputField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    allowOnlyNumbers: Boolean = false,
    limitSymbols: Boolean = false,
    limitSymbolsToCount: Int = 0
) {
    val regex = remember { Regex("^[0-9]*\$") }

    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(64.ldp))
            .border(4.ldp, Black35, RoundedCornerShape(64.ldp)),
        value = value,
        onValueChange = { newValue ->
            if (limitSymbols && newValue.text.length > limitSymbolsToCount) return@TextField
            if (allowOnlyNumbers && !regex.matches(newValue.text)) return@TextField

            onValueChanged(newValue)
        },
        textStyle = textCode.copy(
            fontFamily = getSportFontFamily(),
            textAlign = TextAlign.Center
        ),
        singleLine = true
    )
}

@Composable
fun CodeInputWrapped(
    modifier: Modifier = Modifier,
    title: String,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    isShown: Boolean = true,
    allowOnlyNumbers: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.ldp, Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier.popInOut(
                delay = 200L,
                isShown = isShown
            ),
            text = title,
            style = textTitle.copy(
                color = Black,
                fontFamily = getSportFontFamily(),
            )
        )
        CodeInputField(
            modifier = Modifier
                .size(height = 100.ldp, width = 250.ldp)
                .popInOut(
                    delay = 300L,
                    isShown = isShown
                ),
            value = value,
            onValueChanged = { textFieldValue ->
                onValueChanged(textFieldValue)
            },
            allowOnlyNumbers = allowOnlyNumbers,
            limitSymbols = true,
            limitSymbolsToCount = 2
        )
        Button(
            modifier = Modifier
                .size(width = 120.ldp, height = 50.ldp)
                .popInOut(
                    delay = 400L,
                    isShown = isShown
                ),
            text = "Enter",
            buttonType = ButtonType.Center,
            colorGradient = OrangeList,
            textStyle = textMedium,
            horizontalPadding = 4.ldp
        ) {
            onClick()
        }
    }
}
