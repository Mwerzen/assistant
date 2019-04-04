# Assistant

Prototyped a test-assistant using the Google Cloud's Natural Language processing APIs.

## Functionality
- Extracted contextual information based on who was querying, sentence type, and location of the query.
- Extensible routing system that could take in all parts of speech + context to determine which action/response to take.
- Response generation to add variability into responses.
- Extensible for integrating with additional actions coded in any language.

### Integrations I Built
- Could connect to Youtube, run a video search based on the object of the sentence, depending on the sentence type.
- Could execute an image search based on the subject or object of a sentence, depending on the sentence type.
- Could connect to Wookiepedia via Python extension.
