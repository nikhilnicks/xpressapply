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

import static com.amazon.ask.request.Predicates.requestType;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {

  @Override
  public boolean canHandle(HandlerInput input) {
    return input.matches(requestType(LaunchRequest.class));
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {
    String speechText =
        "Welcome to Xpress Apply Application. You’re pre-approved for a personal loan of up to $25,000 at a maximum APR of 8.98%."
            + "Lock in a competitive fixed interest rate with our quick and simple application. Tell us where you want us to send your loan funds and the amount you need up to your pre-approved amount. Then, select the monthly payment option that works for you.";
    String repromptText = "Would you like to proceed further to start the application ";
    return input.getResponseBuilder()
        .withSimpleCard("ApplySession", speechText)
        .withSpeech(speechText)
        .withReprompt(repromptText)
        .build();
  }
}
