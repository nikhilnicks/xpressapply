/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.sample.xpressapply;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.sample.xpressapply.handlers.CurrentOfferIntentHandler;
import com.sample.xpressapply.handlers.LaunchRequestHandler;
import com.sample.xpressapply.handlers.ProcessOfferCompletedIntentHandler;
import com.sample.xpressapply.handlers.ProcessOfferInProgressIntentHandler;
import com.sample.xpressapply.handlers.ReviseOfferCompletedIntentHandler;
import com.sample.xpressapply.handlers.ReviseOfferInProgressIntentHandler;
import com.sample.xpressapply.handlers.predefined.CancelandStopIntentHandler;
import com.sample.xpressapply.handlers.predefined.FallbackIntentHandler;
import com.sample.xpressapply.handlers.predefined.HelpIntentHandler;
import com.sample.xpressapply.handlers.predefined.SessionEndedRequestHandler;

public class XPressApplyHandler extends SkillStreamHandler {

  public XPressApplyHandler() {
    super(getSkill());
  }

  private static Skill getSkill() {
    return Skills.standard()
        .addRequestHandlers(
            new LaunchRequestHandler(),
            new ReviseOfferCompletedIntentHandler(),
            new ReviseOfferInProgressIntentHandler(),
            new ProcessOfferCompletedIntentHandler(),
            new ProcessOfferInProgressIntentHandler(),
            new CurrentOfferIntentHandler(),
            new CancelandStopIntentHandler(),
            new SessionEndedRequestHandler(),
            new HelpIntentHandler(),
            new FallbackIntentHandler())
        .build();
  }

}
