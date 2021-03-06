import ink.rubi.bilibili.live.connectLiveRoom
import ink.rubi.bilibili.live.handler.simpleMessageHandler
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
object MultiFetchersTest {

    @ObsoleteCoroutinesApi
    @JvmStatic
    fun main(args: Array<String>) {
        val roomIds = arrayOf(115, 92613)
        runBlocking {
            launch(newFixedThreadPoolContext(10, "陆夫人")) {
                connectLiveRoom(roomIds[0], simpleMessageHandler {
                    onReceiveDanmu { user, said ->
                        log.info("$user : $said")
                    }
                })
            }
            launch(newFixedThreadPoolContext(10, "A_PI")) {
                connectLiveRoom(roomIds[1], simpleMessageHandler {
                    onReceiveDanmu { user, said ->
                        log.warn("$user : $said")
                    }
                })
            }
//            delay(1000)
//            job.cancel()
        }
    }
}