# Cursor AI: Tips and Tricks Guide

## Table of Contents
1. [Code Generation Best Practices](#code-generation-best-practices)
2. [Debugging and Troubleshooting](#debugging-and-troubleshooting)
3. [Model Selection Strategy](#model-selection-strategy)
4. [Prompt Engineering](#prompt-engineering)
5. [Workflow: Ask Mode vs Agent Mode](#workflow-ask-mode-vs-agent-mode)
6. [Avoiding Hallucinations](#avoiding-hallucinations)
7. [Quick Reference](#quick-reference)

---

## Code Generation Best Practices

### Structuring Your Prompts

**✅ DO:**
- **Be specific and clear**: Describe exactly what you want to achieve
- **Provide context**: Include relevant code snippets, file paths, and error messages
- **Break down complex tasks**: Divide large tasks into smaller, manageable subtasks
- **Use bullet points**: Organize requirements in a clear, scannable format
- **Specify constraints**: Mention frameworks, versions, coding standards, or limitations

**Example of a well-structured prompt:**
```
I need to create a REST API endpoint with the following requirements:
- Endpoint: POST /api/customers
- Framework: Spring Boot 3.x
- Database: PostgreSQL
- Validation: Use Jakarta Bean Validation
- Response: Return CustomerDTO with status 201 Created
- Error handling: Return 400 Bad Request for validation errors
```

**❌ DON'T:**
- Use vague requests like "make it work" or "fix this"
- Assume the AI knows your entire codebase context
- Request multiple unrelated changes in one prompt
- Skip important details about your environment or requirements

### Task Breakdown Strategy

**When working on complex features:**

1. **First, create a task list:**
   ```
   Task: Implement user authentication
   - Create User entity
   - Create UserRepository
   - Create AuthenticationService
   - Create LoginController
   - Add security configuration
   ```

2. **Work through tasks one at a time:**
   - Complete one task before moving to the next
   - Verify each step works before proceeding
   - Update the task list as you go

3. **Review and iterate:**
   - Test after each major component
   - Refactor if needed before adding more complexity

---

## Debugging and Troubleshooting

### When Code Doesn't Work

**Step-by-step debugging approach:**

1. **Read the error message carefully**
   - Copy the full error stack trace
   - Identify the specific line/file causing the issue
   - Note any relevant context (environment, dependencies, etc.)

2. **Provide complete context to AI:**
   ```
   I'm getting this error:
   [Paste full error message]
   
   This is happening in: [File path]
   My environment: [OS, Java version, framework version]
   Related code: [Paste relevant code snippets]
   ```

3. **Use incremental fixes:**
   - Don't ask for a complete rewrite immediately
   - Try to understand what's wrong first
   - Fix one issue at a time

4. **Verify dependencies:**
   - Check if all required libraries are installed
   - Verify version compatibility
   - Review import statements

### Common Debugging Prompts

**For compilation errors:**
```
I'm getting a compilation error:
[Error message]

In file: [path/to/file.java]
Line: [line number]

Can you help me identify what's wrong?
```

**For runtime errors:**
```
Application crashes with this exception:
[Exception stack trace]

This happens when I [describe the action that triggers it].
Environment: [OS, Java version, etc.]

What could be causing this?
```

**For logical errors:**
```
Expected behavior: [what should happen]
Actual behavior: [what actually happens]

Code snippet:
[relevant code]

Can you help me find the bug?
```

---

## Model Selection Strategy

### Choosing the Right Model Based on Context

**When to use different models:**

#### **Claude Sonnet/Opus (Default - Recommended)**
- ✅ Complex code generation
- ✅ Architecture decisions
- ✅ Code refactoring
- ✅ Understanding large codebases
- ✅ Best for: Production code, critical features

#### **GPT-4**
- ✅ General purpose coding
- ✅ Quick implementations
- ✅ Good balance of speed and quality
- ✅ Best for: Standard CRUD operations, common patterns

#### **GPT-3.5**
- ✅ Simple code snippets
- ✅ Quick questions
- ✅ Fast responses
- ⚠️ May need more iterations for complex tasks
- ✅ Best for: Simple queries, learning

#### **Grok Code (Free Alternative)**
- ✅ When other models hit rate limits
- ✅ Simple code generation
- ✅ Quick prototypes
- ⚠️ May require more refinement
- ✅ Best for: Fallback option, non-critical code

### Context-Based Model Selection

**Use Claude Sonnet/Opus when:**
- Working with complex business logic
- Need deep understanding of existing codebase
- Making architectural decisions
- Debugging intricate issues
- Working with multiple files/interdependencies

**Use GPT-4 when:**
- Standard implementations needed
- Quick feature additions
- Well-defined requirements
- Single-file changes

**Use GPT-3.5 when:**
- Simple questions
- Quick syntax help
- Learning concepts
- Minor fixes

**Use Grok Code when:**
- Other models unavailable
- Simple code snippets
- Prototyping
- Non-critical features

---

## Prompt Engineering

### Effective Prompt Structure

**Template for code generation:**

```
Context:
- Project: [Brief description]
- Framework: [Spring Boot, React, etc.]
- Language: [Java, JavaScript, etc.]

Task:
[Clear description of what needs to be done]

Requirements:
- [ ] Requirement 1
- [ ] Requirement 2
- [ ] Requirement 3

Constraints:
- [Any limitations or specific requirements]

Current code (if relevant):
[Paste relevant code snippets]

Expected outcome:
[What the final result should look like]
```

### Prompt Examples

**Example 1: Creating a new feature**
```
Context:
- Spring Boot 3.x application
- Using JPA/Hibernate
- PostgreSQL database

Task: Create a Customer service layer

Requirements:
- [ ] Create CustomerService interface
- [ ] Implement CRUD operations
- [ ] Add exception handling
- [ ] Include input validation
- [ ] Use CustomerRepository for data access

Current code:
[Paste Customer entity and repository if exists]

Expected outcome:
A complete service layer with proper error handling
```

**Example 2: Fixing a bug**
```
Issue: Customer data not saving to database

Error:
[Paste error message]

Code location:
- File: CustomerService.java
- Method: saveCustomer()

Current code:
[Paste the problematic code]

What I've tried:
- [List attempts to fix]

Expected behavior:
Customer should be saved and return saved entity with ID
```

---

## Workflow: Ask Mode vs Agent Mode

### Understanding the Modes

**Ask Mode:**
- AI provides suggestions and explanations
- No automatic code changes
- Best for: Discussion, clarification, planning
- Use when: You're unsure about the approach

**Agent Mode:**
- AI makes direct code changes
- Can read/write files automatically
- Best for: Implementation, refactoring, fixes
- Use when: You're confident about what needs to be done

### Recommended Workflow

**Step 1: Start in Ask Mode (Discussion Phase)**

```
When you're unsure about changes:
1. Switch to Ask Mode
2. Describe your problem/question
3. Discuss possible solutions
4. Ask for recommendations
5. Get clarification on approach
```

**Example Ask Mode prompt:**
```
I need to add caching to my Customer service. 
What are the best approaches? 
Should I use Spring Cache, Redis, or in-memory cache?
What are the pros/cons of each?
```

**Step 2: Review and Decide**

- Review AI's suggestions
- Ask follow-up questions if needed
- Choose the best approach
- Clarify any doubts

**Step 3: Switch to Agent Mode (Implementation Phase)**

```
Once you're sure about the approach:
1. Switch to Agent Mode
2. Provide clear, specific instructions
3. Let AI implement the changes
4. Review the changes
5. Test the implementation
```

**Example Agent Mode prompt:**
```
Implement Spring Cache for CustomerService:
- Add @Cacheable to findById method
- Add @CacheEvict to save and delete methods
- Configure cache in application.properties
- Use cache name "customers"
```

### When to Use Each Mode

**Use Ask Mode when:**
- ❓ You're not sure about the best approach
- ❓ You need to understand something first
- ❓ You want to discuss trade-offs
- ❓ You're learning a new concept
- ❓ You need clarification on requirements

**Use Agent Mode when:**
- ✅ You know exactly what needs to be done
- ✅ You have clear requirements
- ✅ You want AI to make the changes directly
- ✅ You're implementing a well-defined feature
- ✅ You're confident about the approach

---

## Avoiding Hallucinations

### What Are Hallucinations?

Hallucinations occur when AI generates:
- Code that doesn't exist in your codebase
- Incorrect API usage
- Non-existent methods or classes
- Wrong framework versions or syntax

### Strategies to Prevent Hallucinations

**1. Provide Specific Context**

**❌ Bad:**
```
Create a REST controller for users
```

**✅ Good:**
```
Create a REST controller for users in Spring Boot 3.x:
- Use @RestController annotation
- Use UserService (already exists in com.mv.hibernate.service)
- Follow REST conventions
- Return ResponseEntity<UserDTO>
```

**2. Reference Existing Code**

**✅ Include relevant code:**
```
I have this User entity:
[Paste User.java]

Create a UserController that uses UserService:
[Paste UserService.java if relevant]

Follow the same pattern as CustomerController:
[Paste CustomerController.java]
```

**3. Switch Contexts/Models**

**Technique: Cross-verification**
- Generate code with one model
- Switch to another model (e.g., from Claude to GPT-4)
- Ask the second model to review/verify the code
- This helps catch hallucinations

**Example workflow:**
```
1. Generate code with Claude Sonnet
2. Switch to GPT-4
3. Ask: "Review this code for correctness: [paste code]"
4. Compare responses
5. Use the verified approach
```

**4. Incremental Development**

**Build in small steps:**
- Don't ask for entire features at once
- Generate one class/method at a time
- Verify each step before proceeding
- This reduces hallucination risk

**5. Ask for Verification**

**Before implementing:**
```
Before you make changes, can you:
1. Verify that UserService exists?
2. Check if the method signatures match?
3. Confirm the imports are correct?
```

**6. Use File References**

**Instead of assuming:**
```
❌ "Use the CustomerRepository pattern"
✅ "Use the same pattern as CustomerRepository.java (see file: src/main/java/com/mv/hibernate/repository/CustomerRepository.java)"
```

---

## Quick Reference

### Prompt Templates

#### Code Generation Template
```
Context: [Project/framework details]
Task: [What to build]
Requirements:
- [ ] Item 1
- [ ] Item 2
Files involved: [List file paths]
Expected: [Desired outcome]
```

#### Debugging Template
```
Error: [Full error message]
Location: [File:line]
Environment: [OS, versions]
Code: [Relevant code]
Expected: [What should happen]
Tried: [What you've attempted]
```

#### Refactoring Template
```
Current code: [Paste code]
Issue: [What's wrong or what to improve]
Goal: [What you want to achieve]
Constraints: [Any limitations]
```

### Mode Selection Guide

| Situation | Mode | Why |
|-----------|------|-----|
| Planning a feature | Ask Mode | Discuss approach first |
| Implementing known solution | Agent Mode | Direct implementation |
| Debugging complex issue | Ask Mode → Agent Mode | Understand then fix |
| Learning new concept | Ask Mode | Get explanations |
| Quick fix | Agent Mode | Fast implementation |
| Architecture decision | Ask Mode | Discuss trade-offs |

### Model Selection Quick Guide

| Task Complexity | Recommended Model | Alternative |
|----------------|-------------------|-------------|
| Simple snippet | GPT-3.5 | Grok Code |
| Standard feature | GPT-4 | Claude Sonnet |
| Complex logic | Claude Sonnet/Opus | GPT-4 |
| Architecture | Claude Sonnet/Opus | - |
| Fallback | Grok Code | GPT-3.5 |

### Best Practices Checklist

**Before asking AI:**
- [ ] Have I provided enough context?
- [ ] Are my requirements clear and specific?
- [ ] Have I included relevant code snippets?
- [ ] Am I in the right mode (Ask vs Agent)?
- [ ] Have I chosen the appropriate model?

**After receiving code:**
- [ ] Does it compile?
- [ ] Does it match my requirements?
- [ ] Are imports correct?
- [ ] Does it follow project conventions?
- [ ] Have I tested it?

**To avoid hallucinations:**
- [ ] Did I reference existing code?
- [ ] Did I specify file paths?
- [ ] Did I mention framework versions?
- [ ] Can I verify the generated code?
- [ ] Should I cross-check with another model?

---

## Advanced Tips

### 1. Multi-Step Complex Tasks

**Break down into phases:**

```
Phase 1: Setup
- Create entity classes
- Set up database schema

Phase 2: Data Layer
- Create repositories
- Add custom queries

Phase 3: Service Layer
- Implement business logic
- Add validation

Phase 4: API Layer
- Create controllers
- Add error handling

Phase 5: Testing
- Write unit tests
- Integration tests
```

### 2. Code Review Workflow

```
1. Generate code in Agent Mode
2. Switch to Ask Mode
3. Ask: "Review this code for best practices, potential bugs, and improvements"
4. Implement suggested improvements
5. Test thoroughly
```

### 3. Learning While Coding

**Use Ask Mode for explanations:**
```
After implementing [feature], can you explain:
- How does [specific part] work?
- Why did we use [pattern/approach]?
- What are alternative approaches?
- What are potential pitfalls?
```

### 4. Handling Large Codebases

**Be specific about scope:**
```
I'm working on the Customer module:
- Files: Customer.java, CustomerService.java, CustomerController.java
- I need to add [feature] to this module only
- Don't modify other modules
```

### 5. Version-Specific Code

**Always specify versions:**
```
Framework: Spring Boot 3.2.0
Java: 17
Database: PostgreSQL 15
Hibernate: 6.x
```

---

## Troubleshooting Common Issues

### Issue: AI makes unwanted changes

**Solution:**
- Use Ask Mode first to discuss changes
- Be more specific about what NOT to change
- Use file paths to limit scope
- Review changes before accepting

### Issue: Generated code doesn't match project style

**Solution:**
- Provide examples of existing code style
- Specify coding conventions
- Ask AI to follow existing patterns
- Use Ask Mode to clarify style first

### Issue: AI doesn't understand context

**Solution:**
- Provide more background information
- Include related files
- Explain the business logic
- Break down into smaller tasks

### Issue: Code has errors or doesn't compile

**Solution:**
- Copy full error message
- Include stack trace
- Provide environment details
- Ask AI to fix specific errors one by one

---

## Summary: The Golden Rules

1. **Be Specific**: Provide clear, detailed requirements
2. **Provide Context**: Include relevant code and environment details
3. **Start in Ask Mode**: Discuss before implementing
4. **Break Down Tasks**: Work incrementally
5. **Verify Everything**: Test and review generated code
6. **Choose Right Model**: Match model to task complexity
7. **Cross-Check**: Use multiple models to verify critical code
8. **Learn Continuously**: Use Ask Mode to understand, not just implement

---

## Additional Resources

- Cursor Documentation: [Cursor AI Docs]
- Prompt Engineering Guide: [Best Practices]
- Model Comparison: [When to use which model]

---

*Last Updated: [Current Date]*
*Version: 1.0*
