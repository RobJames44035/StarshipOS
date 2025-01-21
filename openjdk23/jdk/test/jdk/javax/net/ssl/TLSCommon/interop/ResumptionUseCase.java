/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * The SSL/TLS communication parameters on session resumption.
 */
public class ResumptionUseCase extends ExtUseCase {

    private boolean enableServerSessionTicket
            = Boolean.getBoolean("jdk.tls.server.enableSessionTicketExtension");

    private boolean enableClientSessionTicket
            = Boolean.getBoolean("jdk.tls.client.enableSessionTicketExtension");

    public static ResumptionUseCase newInstance() {
        return new ResumptionUseCase();
    }

    public boolean isEnableServerSessionTicket() {
        return enableServerSessionTicket;
    }

    public ResumptionUseCase setEnableServerSessionTicket(
            boolean enableServerSessionTicket) {
        this.enableServerSessionTicket = enableServerSessionTicket;
        return this;
    }

    public boolean isEnableClientSessionTicket() {
        return enableClientSessionTicket;
    }

    public ResumptionUseCase setEnableClientSessionTicket(
            boolean enableClientSessionTicket) {
        this.enableClientSessionTicket = enableClientSessionTicket;
        return this;
    }

    /*
     * For TLS 1.3, the session should always be resumed via session ticket.
     * For TLS 1.2, if both of server and client support session ticket,
     * the session should be resumed via session ticket; otherwise, the session
     * should be resumed via session ID.
     */
    public ResumptionMode expectedResumptionMode() {
        if (getProtocol() == Protocol.TLSV1_3
                || (enableServerSessionTicket && enableClientSessionTicket)) {
            return ResumptionMode.TICKET;
        } else {
            return ResumptionMode.ID;
        }
    }

    @Override
    public String toString() {
        return Utilities.join(Utilities.PARAM_DELIMITER,
                super.toString(),
                Utilities.joinNameValue(
                        "enableServerSessionTicket", enableServerSessionTicket),
                Utilities.joinNameValue(
                        "enableClientSessionTicket", enableClientSessionTicket));
    }
}
