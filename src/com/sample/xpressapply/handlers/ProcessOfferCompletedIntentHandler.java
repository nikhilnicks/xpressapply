/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.sample.xpressapply.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentConfirmationStatus;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import java.util.Map;
import java.util.Optional;

public class ProcessOfferCompletedIntentHandler implements RequestHandler {


  @Override
  public boolean canHandle(HandlerInput input) {
    System.out.println("Request Type : " + input.getRequestEnvelope().getRequest().getType());
    System.out.print("trying complete , state : " + (
        input.getRequestEnvelope().getRequest().getType().matches("IntentRequest")
            ? ((IntentRequest) input.getRequestEnvelope().getRequest()).getDialogState() : ""));
    return input.getRequestEnvelope().getRequest().getType().matches("IntentRequest") &&
        input.matches(intentName("processoffer"))
        && ((IntentRequest) input.getRequestEnvelope().getRequest()).getDialogState()
        .equals(DialogState.COMPLETED);
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {

    Request request = input.getRequestEnvelope().getRequest();
    IntentRequest intentRequest = (IntentRequest) request;
    Intent intent = intentRequest.getIntent();
    Map<String, Slot> slots = intent.getSlots();
    System.out.print("handling complete request - process offer");
    boolean restart = false;
    System.out.println(slots);

    if (intentRequest.getIntent().getConfirmationStatus().equals(IntentConfirmationStatus.DENIED)) {

      AttributesManager attributesManager = input.getAttributesManager();

      Map<String, Object> sessionAttributes = attributesManager.getSessionAttributes();

      sessionAttributes.putAll(intent.getSlots());

      attributesManager.setSessionAttributes(sessionAttributes);

      return input.getResponseBuilder()
          .withSpeech(
              "Your application is cancelled. You can say start application to accept the current offer or say better offer to receive a customized quote.")
          .withReprompt(
              "Your application is cancelled. You can say start application to accept the current offer or say better offer to receive a customized quote.")
          .build();
    }

    String speechText;
    String acctNbr = slots
        .get("acctNbr").getValue();

    String routingNbr = slots
        .get("routingNbr").getValue();

    speechText =
        "<speak>Your loan application is processed to account number <say-as interpret-as='spell-out'>"
            + acctNbr
            + "</say-as> with routing number <say-as interpret-as='spell-out'>" + routingNbr
            + "</say-as></speak>";

    return input.getResponseBuilder()
        .withSpeech(speechText)
        .withSimpleCard("ApplySession", speechText)
        .build();
  }
}