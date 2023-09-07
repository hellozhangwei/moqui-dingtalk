/*
 * This software is in the public domain under CC0 1.0 Universal plus a 
 * Grant of Patent License.
 * 
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 * 
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package org.moqui.dingtalk

import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse
import com.aliyun.teaopenapi.models.Config
import com.aliyun.teautil.models.RuntimeOptions
import groovy.json.JsonBuilder
import org.moqui.context.ExecutionContext
import org.moqui.util.SystemBinding

class DingTalk {
    protected final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DingTalk.class)

    static com.aliyun.dingtalkoauth2_1_0.Client authClient() throws Exception {
        Config config = new Config()
        config.protocol = "https"
        config.regionId = "central"
        return new com.aliyun.dingtalkoauth2_1_0.Client(config)
    }

    static String getAccessToken(String authCode) throws Exception {
        com.aliyun.dingtalkoauth2_1_0.Client client = authClient()

        String clientSecret = SystemBinding.getPropOrEnv("dingtalk_client_secret")
        String clientId = SystemBinding.getPropOrEnv("dingtalk_client_id")

        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setCode(authCode)
                .setGrantType("authorization_code")

        GetUserTokenResponse getUserTokenResponse = client.getUserToken(getUserTokenRequest)
        //获取用户个人token
        String accessToken = getUserTokenResponse.getBody().getAccessToken()
        return  getUserinfo(accessToken)

    }

    static com.aliyun.dingtalkcontact_1_0.Client contactClient() throws Exception {
        Config config = new Config()
        config.protocol = "https"
        config.regionId = "central"
        return new com.aliyun.dingtalkcontact_1_0.Client(config)
    }

    static String getUserinfo(String accessToken) throws Exception {
        com.aliyun.dingtalkcontact_1_0.Client client = contactClient()
        GetUserHeaders getUserHeaders = new GetUserHeaders()
        getUserHeaders.xAcsDingtalkAccessToken = accessToken
        //获取用户个人信息，如需获取当前授权人的信息，unionId参数必须传me
        GetUserResponseBody me = client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody()

        println("============me======" + (new JsonBuilder( me )).toPrettyString())
        return me
    }
}
