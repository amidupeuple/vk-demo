package com.pesiykot.vkdemo

import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.client.{TransportClient, VkApiClient}
import com.vk.api.sdk.exceptions.OAuthException
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.UserAuthResponse
import com.vk.api.sdk.objects.apps.responses.GetResponse
import com.vk.api.sdk.objects.wall.responses.GetByIdExtendedResponse

object Runner extends App {
  val CODE = "1156190fe24e91669e"
  val APP_ID = 6830800
  val CLIENT_SECRET = "kPnrXt4N6FSCkmH0ddbY"
  val REDIRECT_URI = "http://vk.com"

  val transportClient: TransportClient = HttpTransportClient.getInstance()
  val vk: VkApiClient = new VkApiClient(transportClient)
  var authResponse: UserAuthResponse = null

  try {
    authResponse = vk.oauth().userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, CODE).execute()
  } catch {
    case e: OAuthException => e.getRedirectUri
  }

  val actor: UserActor = new UserActor(authResponse.getUserId, authResponse.getAccessToken)
  val getResponse = vk.wall().get(actor).ownerId(1)
    .count(100)
    .offset(5)
    .execute();

}
