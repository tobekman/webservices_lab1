module core {
    requires com.fasterxml.jackson.databind;
    requires utils;
    requires spi;
    uses spi.response.Response;
}