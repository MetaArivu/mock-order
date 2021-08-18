/**
 * (C) Copyright 2021 Araf Karsh Hamid 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.adapters.controllers;

import io.fusion.air.microservice.ServiceBootStrap;
import io.fusion.air.microservice.domain.models.*;
import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.config.ServiceHelp;
import io.fusion.air.microservice.server.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * App Controller for the Service
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@Configuration
@RestController
// "/api/v1/order"
@RequestMapping("${service.api.path}")
@RequestScope
@Tag(name = "Order", description = "Order Service ")
public class AppControllerImpl extends AbstractController {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
	@Autowired
	private ServiceConfiguration serviceConfig;

	private String serviceName;

	/**
	 * Get the Order Status
	 * 
	 * @return
	 */
    @Operation(summary = "Check the Order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Order Status Check",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Invalid Order Reference No.",
            content = @Content)
    })
	@GetMapping("/status/{orderId}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getStatus(@PathVariable("orderId") String _orderId,
			HttpServletRequest request) throws Exception {
		log.info("|"+name()+"|Request to Order Status of Service... ");
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("OrderID", _orderId);
		status.put("Message","Order is waiting for dispatch.");
		return ResponseEntity.ok(status);
	}

	/**
	 * Process the Order
	 */
    @Operation(summary = "Process Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Process the Order",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Unable to process the Order",
            content = @Content)
    })
    @PostMapping("/process")
    public ResponseEntity<Map<String,Object>> processPayments(@RequestBody OrderEntity _orderEntity) {
		log.info("|"+name()+"|Request to process payments... ");
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("OrderID", _orderEntity.getOrderId());
		status.put("OrderStatus", OrderStatus.PAID.toString());
		status.put("Message","Order Status = "+OrderStatus.PAID.toString());
		return ResponseEntity.ok(status);
    }

	/**
	 * Cancel the Order
	 */
	@Operation(summary = "Cancel Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Cancel the Order",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
					description = "Unable to Cancel the Order",
					content = @Content)
	})
	@DeleteMapping("/cancel/{orderId}")
	public ResponseEntity<Map<String,Object>> cancel(@PathVariable("orderId") String _orderId) {
		log.info("|"+name()+"|Request to process payments... ");
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("OrderID", _orderId);
		status.put("Message","Order Cancelled!");
		return ResponseEntity.ok(status);
	}

	/**
	 * Update the Order
	 */
	@Operation(summary = "Update Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Update the Order",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
					description = "Unable to Update the payment",
					content = @Content)
	})
	@PutMapping("/update/{orderId}")
	public ResponseEntity<Map<String,Object>> updateOrder(@PathVariable("orderId") String _orderId) {
		log.info("|"+name()+"|Request to Update payments... "+_orderId);
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("OrderID", _orderId);
		status.put("Message","Order updated!");
		return ResponseEntity.ok(status);
	}
 }

