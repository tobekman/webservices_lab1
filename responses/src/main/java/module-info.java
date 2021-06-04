import plugins.responses.SendAddResponse;
import plugins.responses.SendDuckFileResponse;
import plugins.responses.SendIndexFileResponse;
import plugins.responses.SendMessagesResponse;

module responses {
    requires spi;
    requires persistence;
    requires com.fasterxml.jackson.databind;
    provides spi.response.Response with SendMessagesResponse
            , SendIndexFileResponse
            , SendDuckFileResponse
            , SendAddResponse;
    opens plugins.responses to core;
}