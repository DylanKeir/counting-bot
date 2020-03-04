package eu.dyl4n.countingbot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.MessageUpdateEvent
import net.dv8tion.jda.api.hooks.EventListener

class BotManager {

    static def jda

    BotManager() {
        jda = new JDABuilder("token")
                .addEventListeners(new EventListener() {
                    @Override
                    void onEvent(GenericEvent event) {
                        if (!(event instanceof MessageReceivedEvent)) {
                            return
                        }

                        def msg = event.message

                        if (msg.channel.idLong != 684631265596407838) {
                            return
                        }

                        def i = getNumberFromMessage(msg, true)
                        def lastMsg = msg.channel.history.retrievePast(2).complete().get(1)
                        def lastMsgI = getNumberFromMessage(lastMsg, false)

                        try {
                            if (i == (lastMsgI as int) + 1) {
                                return
                            }
                        } catch (Exception e) {
                            msg.delete()
                        }

                        msg.delete().complete()
                    }
                }, new EventListener() {
                    @Override
                    void onEvent(GenericEvent event) {
                        if (!(event instanceof MessageUpdateEvent)) {
                            return
                        }

                        def msg = event.getMessage()
                        if (msg.channel.idLong != 684631265596407838) {
                            return
                        }

                        msg.delete().complete()
                    }
                }).build()
    }

    static def getNumberFromMessage(Message msg) throws NumberFormatException {
        return Integer.parseInt(msg.contentDisplay)
    }

    static def getNumberFromMessage(Message msg, boolean delete) {
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
