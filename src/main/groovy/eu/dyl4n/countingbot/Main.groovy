package eu.dyl4n.countingbot

import net.dv8tion.jda.core.JDA

class Main {

    static def botManager

    static void main(String[] args) {
        this.botManager = new BotManager()

        Thread.currentThread().addShutdownHook {
            ((this.botManager as BotManager).jda as JDA).shutdownNow()
        }
    }

}
