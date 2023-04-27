package com.masharo.habitstrackercompose.ui.screen.applicationInfo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.ui.theme.BGBusinessCard
import com.masharo.habitstrackercompose.ui.theme.BGBusinessCardLines
import com.masharo.habitstrackercompose.ui.theme.GreenBusinessCard

@Composable
fun ApplicationInfoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = BGBusinessCard
            )
    ) {
        Spacer(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .fillMaxWidth()
                .height(10.dp)
        )
        AppHabitTrackerInfoHorizontalPager()
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun AppHabitTrackerInfoHorizontalPager() {
    HorizontalPager(
        modifier = Modifier,
        pageCount = 2
    ) { page ->
        when (page) {
            0 -> {
                AppDescription()
            }

            1 -> {
                BusinessCardBody(
                    name = stringResource(R.string.name_application_info),
                    profession = stringResource(R.string.body_application_info)
                )
            }
        }
    }
}

@Composable
fun AppDescription(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Image(
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .padding(0.dp),
            painter = painterResource(R.drawable.habit_tracker_logo),
            contentDescription = null,
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Text(
            modifier = Modifier
                .padding(top = 40.dp),
            text = stringResource(R.string.app_name),
            color = Color.White,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            modifier = Modifier
                .padding(vertical = 20.dp),
            color = Color.White,
            text = stringResource(R.string.application_description)
        )
    }
}

@Composable
fun BusinessCardBody(
    modifier: Modifier = Modifier,
    name: String,
    profession: String
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Person(
            name = name,
            profession = profession
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        MyContacts()
    }
}

@Composable
private fun MyContacts() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 30.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactHabit(
            image = R.drawable.ic_baseline_phone_24,
            data = R.string.phone_number,
            description = R.string.phone_number_desc
        )
        ContactHabit(
            image = R.drawable.ic_baseline_share_24,
            data = R.string.share,
            description = R.string.share_desc
        )
        ContactHabit(
            image = R.drawable.ic_baseline_mail_24,
            data = R.string.mail,
            description = R.string.mail_desc
        )
    }
}

@Composable
fun Person(
    name: String,
    profession: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .padding(0.dp),
            painter = painterResource(
                id = R.drawable.android_logo
            ),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(
                    bottom = 9.dp
                ),
            text = name,
            fontSize = 45.sp,
            color = Color.White,
            fontWeight = FontWeight.W300
        )
        Text(
            text = profession,
            color = GreenBusinessCard,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactHabit(
    @DrawableRes image: Int,
    @StringRes description: Int,
    @StringRes data: Int
) {
    Column {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = BGBusinessCardLines
                )
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 60.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(description)
            )
            Spacer(
                modifier = Modifier
                    .width(18.dp)
            )
            Text(
                text = stringResource(data),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}