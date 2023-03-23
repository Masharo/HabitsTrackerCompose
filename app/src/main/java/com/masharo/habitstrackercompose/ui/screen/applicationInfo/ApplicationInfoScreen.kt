package com.masharo.habitstrackercompose.ui.screen.applicationInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.Contact
import com.masharo.habitstrackercompose.ui.theme.BGBusinessCard
import com.masharo.habitstrackercompose.ui.theme.BGBusinessCardLines
import com.masharo.habitstrackercompose.ui.theme.GreenBusinessCard
import com.masharo.habitstrackercompose.ui.theme.md_theme_light_primaryContainer

@Composable
fun ApplicationInfoScreen() {
    BusinessCardBody(
        name = "Nikita",
        profession = "profession"
    )
}

@Composable
fun BusinessCardBody(
    modifier: Modifier = Modifier,
    name: String,
    profession: String
) {
    Box(
        modifier = modifier
            .background(
                color = BGBusinessCard
            )
    ) {
        Person(
            name = name,
            profession = profession
        )
        ContactsData(
            contacts = listOf(
                Contact(
                    img = painterResource(
                        id = R.drawable.ic_baseline_phone_24
                    ),
                    value = stringResource(
                        id = R.string.phone_number
                    ),
                    desc = stringResource(
                        id = R.string.phone_number_desc
                    )
                ),
                Contact(
                    img = painterResource(
                        id = R.drawable.ic_baseline_share_24
                    ),
                    value = stringResource(
                        id = R.string.share
                    ),
                    desc = stringResource(
                        id = R.string.share_desc
                    )
                ),
                Contact(
                    img = painterResource(
                        id = R.drawable.ic_baseline_mail_24
                    ),
                    value = stringResource(
                        id = R.string.mail
                    ),
                    desc = stringResource(
                        id = R.string.mail_desc
                    )
                )
            )
        )
        Box(
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
    }


}

@Composable
fun Person(
    name: String,
    profession: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.30f)
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
fun ContactsData(
    contacts: List<Contact>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 30.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        contacts.forEach { contact ->
            ContactData(
                image = contact.img,
                description = contact.desc,
                data = contact.value
            )
        }

    }
}

@Composable
fun ContactData(
    image: Painter,
    description: String,
    data: String
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
                painter = image,
                contentDescription = description
            )
            Spacer(
                modifier = Modifier
                    .width(18.dp)
            )
            Text(
                text = data,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}