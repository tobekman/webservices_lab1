import plugins.responses.*;

module responses {
    requires spi;
    requires persistence;
    requires com.fasterxml.jackson.databind;
    provides spi.response.Response with SendMessagesResponse
            , SendIndexFileResponse
            , SendDuckFileResponse
            , SendAddResponse
            , SendGreetingResponse;
    opens plugins.responses to core;
}