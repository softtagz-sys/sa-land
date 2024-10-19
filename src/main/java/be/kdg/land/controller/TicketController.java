package be.kdg.land.controller;

import be.kdg.land.controller.dto.out.PayloadDeliveryDto;
import be.kdg.land.controller.dto.tickets.PayloadDeliveryTicket;
import be.kdg.land.controller.dto.tickets.WeighBridgeTicket;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;
    private final SpringTemplateEngine templateEngine;

    public TicketController(TicketService ticketService, SpringTemplateEngine templateEngine) {
        this.ticketService = ticketService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/available-tickets")
    public ModelAndView getLicensePlateData(@RequestParam(value = "licensePlate", required = false) String licensePlate) {
        ModelAndView modelAndView = new ModelAndView("tickets/ticket_overview");

        if (licensePlate != null && !licensePlate.isEmpty()) {

            List<PayloadDelivery> deliveries = ticketService.getAllPayLoadDeliveries(licensePlate);

            List<PayloadDeliveryDto> deliveryDtos= deliveries.stream().map(pd -> new PayloadDeliveryDto(
                    pd.getPayloadDeliveryId().toString(),
                    pd.getCustomer().getName(),
                    pd.getEntryWeighing() != null? pd.getEntryWeighing().getTimestamp(): null,
                    pd.getExitWeighing() != null && pd.getExitWeighing().getTimestamp() != null,
                    pd.getExitWeighing() != null
                    )).toList();

            modelAndView.addObject("licensePlate", licensePlate);
            modelAndView.addObject("payloadDeliveries", deliveryDtos);
        }

        return modelAndView;
    }

    @GetMapping(value = "/wbt-ticket/{id}", produces = {"application/pdf"})
    public @ResponseBody ResponseEntity<byte[]> getWBTTicket(@PathVariable("id") String payloadDeliveryId) throws IOException {
        var pageContents = parseWbtTicketTemplate(UUID.fromString(payloadDeliveryId));
        var filePath = generatePdfFromHtml(pageContents)
                .map(Paths::get);
        if (filePath.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(Files.readAllBytes(filePath.get()));
    }

    @GetMapping(value = "/pdt-ticket/{id}", produces = {"application/pdf"})
    public @ResponseBody ResponseEntity<byte[]> getPDTTicket(@PathVariable("id") String payloadDeliveryId) throws IOException {
        var pageContents = parsePdtTicketTemplate(UUID.fromString(payloadDeliveryId));
        var filePath = generatePdfFromHtml(pageContents)
                .map(Paths::get);
        if (filePath.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(Files.readAllBytes(filePath.get()));
    }

    private Optional<String> generatePdfFromHtml(String html) {
        String filePath = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return Optional.of(filePath);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    private String parsePdtTicketTemplate(UUID payloadDeliveryTicketId) {

        Context context = new Context();

        context.setVariable("pdt", getPdt(payloadDeliveryTicketId));

        return templateEngine.process("/tickets/pdt_ticket", context);
    }

    private String parseWbtTicketTemplate(UUID payloadDeliveryTicketId) {

        Context context = new Context();

        context.setVariable("wbt", getWbt(payloadDeliveryTicketId));

        return templateEngine.process("/tickets/wbt_ticket", context);
    }


    private PayloadDeliveryTicket getPdt(UUID payloadDeliveryTicketId) {
        Optional<PayloadDelivery> pdOpt = ticketService.getPayloadDelivery(payloadDeliveryTicketId);
        if (pdOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery with id " + payloadDeliveryTicketId + " not found");
        }
        PayloadDelivery pd = pdOpt.get();

        return new PayloadDeliveryTicket(
                pd.getPayloadDeliveryId(),
                pd.getWarehouse().getWarehouseId(),
                pd.getRawMaterial().getName(),
                pd.getEntry().getTimeStamp().toLocalDate()
        );
    }


    private WeighBridgeTicket getWbt(UUID payloadDeliveryTicketId) {
        Optional<PayloadDelivery> pdOpt = ticketService.getPayloadDelivery(payloadDeliveryTicketId);
        if (pdOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery with id " + payloadDeliveryTicketId + " not found");
        }
        PayloadDelivery pd = pdOpt.get();

        return new WeighBridgeTicket(
                pd.getPayloadDeliveryId(),
                pd.getEntryWeighing().getWeight(),
                pd.getExitWeighing().getWeight(),
                pd.getEntryWeighing().getWeight() - pd.getExitWeighing().getWeight(),
                pd.getEntry().getTimeStamp(),
                pd.getLicensePlate()
        );
    }


}


