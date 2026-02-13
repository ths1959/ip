# AI Usage Disclosure

## Project: MemoMax
**Tool Used:** ChatGPT (OpenAI)

## Overview of AI Usage
ChatGPT served as a technical assistant to brainstorm implementation strategies, clarify JavaFX concepts, and ensure adherence to coding standards.

---

## 1. Logic and Feature Design
### Feature Refinement: Update Logic
* **Assistance:** Discussed strategies for modifying tasks within the TaskList.
* **Outcome:** Clarified the process of replacing objects by index while preserving status, ensuring the "C-Update" extension functioned correctly.

### UX Improvements: Shutdown Sequence
* **Assistance:** Sought advice on a user-friendly exit sequence.
* **Outcome:** Implemented a delayed closure using a transition timer, allowing the goodbye message to be read before the application terminates.

---

## 2. Code Quality and Refactoring
### Applying SLAP
* **Assistance:** Reviewed methods for Single Level of Abstraction Principle compliance.
* **Outcome:** Identified mixed abstraction levels in storage logic; refactored parsing details into individual task classes based on these insights.

### JavaDoc and Standards
* **Assistance:** Verified that headers met documentation requirements.
* **Outcome:** Ensured all public APIs used correct tags, maintaining high readability and consistency.

---

## 3. Testing and Troubleshooting
### Test Case Ideation
* **Assistance:** Identified potential edge cases for task operations.
* **Outcome:** Structured JUnit tests to account for out-of-bounds indices and invalid inputs.

### Environment and Resources
* **Assistance:** Troubleshot resource loading issues for images and FXML.
* **Outcome:** Clarified JavaFX resource stream handling within Gradle, enabling a successful JAR build.

---

## Observations
* **Efficiency:** Effective for resolving JavaFX "how-to" queries and identifying minor syntax errors.
* **Accuracy:** Manual verification was performed to ensure all solutions remained compatible with Java 17.