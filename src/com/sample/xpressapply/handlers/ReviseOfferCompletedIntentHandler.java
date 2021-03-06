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


  @Override
  public boolean canHandle(HandlerInput input) {
    System.out.println("Request Type : " + input.getRequestEnvelope().getRequest().getType());
    System.out.print("trying complete , state : " + (
        input.getRequestEnvelope().getRequest().getType().matches("IntentRequest")
            ? ((IntentRequest) input.getRequestEnvelope().getRequest()).getDialogState() : ""));
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
    System.out.print("handling complete request");
    boolean restart = false;
    // System.out.println(slots);
    String speechText;
    String amount = slots
        .get("amount").getValue();

    String terms = slots
        .get("terms").getValue();

    String apr = slots
        .get("apr").getValue();

    restart = Integer.valueOf(amount) < 50000;
    //TODO: identify the flag and speech text accordingly.
    {
      if (restart) {
        speechText = "<speak>You loan is approved for " + amount
            + " for <say-as interpret-as='spell-out'>APR</say-as> " + apr + "% under terms of "
            + terms + " </speak>";
        return input.getResponseBuilder()
            .withSpeech(speechText)
            .withSimpleCard("ApplySession", speechText)
            .withShouldEndSession(false)
            //.addDelegateDirective(Intent.builder().withName("processoffer").withConfirmationStatus(IntentConfirmationStatus.NONE).build())
            .build();
      } else {
        System.out.println("Trying to restart the intent again after completion");
        speechText =
            "<speak>We are unable to approve the loan amount that you have requested for the terms and <say-as interpret-as='spell-out'>APR</say-as> at the moment. "
                + "Please Say <emphasis level='strong'>Start Over</emphasis> to restart your application</speak>";
        return input.getResponseBuilder()
            .withSpeech(speechText)
            .withShouldEndSession(false)
            .withSimpleCard("ApplySession", speechText)
            .build();
      }
    }
  }
}
