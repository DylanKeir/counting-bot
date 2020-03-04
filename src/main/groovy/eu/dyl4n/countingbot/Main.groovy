package eu.dyl4n.countingbot

import net.dv8tion.jda.api.JDA

class Main {

    static def botManager

    static void main(String[] args) {
        botManager = new BotManager()

        Thread.currentThread().addShutdownHook {
            ((botManager as BotManager).jda as JDA).shutdownNow()
        }
    }

}
