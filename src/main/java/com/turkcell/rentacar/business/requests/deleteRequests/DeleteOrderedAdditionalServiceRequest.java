package com.turkcell.rentacar.business.requests.deleteRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrderedAdditionalServiceRequest {
	private int orderedAdditionalServiceId;
}
