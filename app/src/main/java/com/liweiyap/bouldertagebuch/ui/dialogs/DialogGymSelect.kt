package com.liweiyap.bouldertagebuch.ui.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liweiyap.bouldertagebuch.R
import com.liweiyap.bouldertagebuch.model.Gym
import com.liweiyap.bouldertagebuch.model.GymId
import com.liweiyap.bouldertagebuch.model.gymRockerei
import com.liweiyap.bouldertagebuch.model.gymVels
import com.liweiyap.bouldertagebuch.ui.MainViewModel
import com.liweiyap.bouldertagebuch.ui.components.AppDialog
import com.liweiyap.bouldertagebuch.ui.components.AppTextButton
import com.liweiyap.bouldertagebuch.ui.theme.AppTheme

@Composable
fun DialogGymSelect(
    onDismissRequest: () -> Unit,
    viewModel: MainViewModel,
    onRequestDifficultySelectDialog: () -> Unit,
) {
    DialogGymSelect(
        onDismissRequest = onDismissRequest,
        userDefinedGym = viewModel.userDefinedGym.collectAsState().value,
        onGymSelected = viewModel::setTodayGymId,
        onRequestDifficultySelectDialog = onRequestDifficultySelectDialog,
    )
}

@Composable
fun DialogGymSelect(
    onDismissRequest: () -> Unit = {},
    userDefinedGym: Gym? = null,
    onGymSelected:(GymId) -> Unit = {},
    onRequestDifficultySelectDialog: () -> Unit = {},
) {
    AppDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.title_dialog_gym_select),
    ) {
        DialogGymSelectButton(
            text = gymRockerei.name,
            marginBottom = 8.dp,
        ) {
            onGymSelected(gymRockerei.id)
            onDismissRequest()
            onRequestDifficultySelectDialog()
        }

        DialogGymSelectButton(
            text = gymVels.name,
        ) {
            onGymSelected(gymVels.id)
            onDismissRequest()
            onRequestDifficultySelectDialog()
        }

        if (userDefinedGym == null) {
            DialogGymSelectButton(
                text = stringResource(id = R.string.button_dialog_gym_select_create_new).uppercase(),
                fontWeight = FontWeight.Bold,
                marginTop = 24.dp,
            )
        }
        else {
            DialogGymSelectButton(
                text = userDefinedGym.name,
                marginTop = 8.dp,
            )
        }
    }
}

@Composable
private fun DialogGymSelectButton(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    marginTop: Dp = 0.dp,
    marginBottom: Dp = 0.dp,
    onGymSelected:() -> Unit = {},
) {
    AppTextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = marginTop, bottom = marginBottom),
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        shape = MaterialTheme.shapes.medium,
        onClick = onGymSelected,
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DialogGymSelectPreview() {
    AppTheme {
        DialogGymSelect()
    }
}