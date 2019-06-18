package eu.dyl4n.countingbot


import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.MessageUpdateEvent
import net.dv8tion.jda.core.hooks.EventListener

class BotManager {

    static def jda

    BotManager() {
        jda = new JDABuilder("aa")
                .addEventListener(new EventListener() {
                    @Override
                    void onEvent(Event event) {
                        if (!(event instanceof MessageReceivedEvent)) {
                            return
                        }

                        def msg = event.message

                        if (msg.channel.idLong != 571152815003795477) {
                            return
                        }

                        def i = getNumberFromMessage msg, true
                        def lastMsg = msg.channel.history.retrievePast(2).complete().get 1
                        def lastMsgI = getNumberFromMessage lastMsg, false

                        try {
                            if (i == (lastMsgI as int) + 1) {
                                return
                            }
                        } catch (Exception e) {
                            msg.delete()
                        }

                        msg.delete().complete()
                    }
                }).addEventListener(new EventListener() {
                    @Override
                    void onEvent(Event event) {
                        if (!(event instanceof MessageUpdateEvent)) {
                            return
                        }

                        def msg = event.getMessage()
                        if (msg.channel.idLong != 571152815003795477) {
                            return
                        }

                        msg.delete().complete()
                    }
                }).build()
    }

    def getNumberFromMessage(Message msg) throws NumberFormatException {
        return Integer.parseInt(msg.contentDisplay)
    }

    def getNumberFromMessage(Message msg, boolean delete) {
        try {
            return getNumberFromMessage(msg)
        } catch (NumberFormatException e) {
            if (!delete) {
                return
            }

            msg.delete()
        }
    }

}
