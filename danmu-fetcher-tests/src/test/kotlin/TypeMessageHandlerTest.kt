import ink.rubi.bilibili.live.danmu.DanmuListener.receiveDanmu
import ink.rubi.bilibili.live.danmu.handler.typedMessageHandler
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
object TypeMessageHandlerTest {

    @JvmStatic
    fun main(args: Array<String>) {
//        val roomId = 734
        val roomId = 92613
        runBlocking {
            val job = launch {
                receiveDanmu(roomId) {
                    typedMessageHandler {
                        onReceiveDanmu { user, badge, userLevel, said ->
                            log.info("user          : $user")
                            log.info("badge         : $badge")
                            log.info("userLevel     : $userLevel")
                            log.info("said          : $said")
                        }
                        onReceiveGift { gift ->
                            log.info("user          : ${gift.uname}")
                            log.info("num           : ${gift.num}")
                            log.info("giftName      : ${gift.giftName}")
                        }
                        onSomeOneEnterInLiveRoom { log.info("[$it] 进入了直播间") }
                        onGuardEnterInLiveRoom { log.info("[舰长][$it] 进入了直播间") }
                        onUnknownTypeMessage { message, _ ->
                            log.warn(message)
                        }
//                        onAllTypeMessage { log.error(it) }
                        onLive { log.info("[$it] 开始直播") }
                        onPrepare { log.info("[$it] 准备直播") }
                        onRoomRankChange {
                            log.info("roomid        :${it.roomid}")
                            log.info("room_rank     :${it.rank_desc}")
                        }

                        onError { _, e -> log.error("catch exception :", e) }
                    }
                }
            }
        }
    }


}