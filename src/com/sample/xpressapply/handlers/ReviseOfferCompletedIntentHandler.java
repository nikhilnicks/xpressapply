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

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import java.util.Map;
import java.util.Optional;

public class ReviseOfferCompletedIntentHandler implements RequestHandler {

  public static final String COLOR_KEY = "COLOR";
  public static final String COLOR_SLOT = "Color";

  @Override
  public boolean canHandle(HandlerInput input) {
    System.out.println("Can Completed");
    return input.getRequestEnvelope().getRequest().getType().matches("IntentRequest") &&
        input.matches(intentName("ReviseOffer"))
        && ((IntentRequest) input.getRequestEnvelope().getRequest()).getDialogState()
        .equals(DialogState.COMPLETED);
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {

    Request request = input.getRequestEnvelope().getRequest();
    IntentRequest intentRequest = (IntentRequest) request;
    Intent intent = intentRequest.getIntent();
    Map<String, Slot> slots = intent.getSlots();
    System.out.println("handling inprogres request");

    System.out.println(slots);

    String speechText;
    String amount = slots
        .get("amount").getValue();

    String terms = slots
        .get("terms").getValue();

    String apr = slots
        .get("apr").getValue();

    speechText =
        "You loan is approved for " + amount + "for APR : " + apr + "under terms of : " + terms;

    return input.getResponseBuilder()
        .withSpeech(speechText)
        .withSimpleCard("ApplySession", speechText)
        .build();
  }
}
