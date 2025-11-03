# Developer Guide Fixes

This document tracks all fixes made to the Developer Guide (DG) to align it with the actual implementation.

---

## Fix #1: Duplicate Contact Detection - Phone Only

**Date**: 3 November 2025  
**Issue**: DG stated that KnotBook checks for duplicates by "phone OR email", but the actual implementation only checks by phone number.  
**Reference**: GitHub Issue - `type.DocumentationBug` - Opened 2 days ago

### Changes Made

**File**: `docs/DeveloperGuide.md`

**Location 1 - Main Flow (Line 632)**
- **Before**: "KnotBook checks for duplicate contacts (same phone or email)"
- **After**: "KnotBook checks for duplicate contacts (same phone number)"

**Location 2 - Extension Case 8a (Line 694)**
- **Before**: "8a. Duplicate contact detected (same phone or email)."
- **After**: "8a. Duplicate contact detected (same phone number)."

### Rationale

The actual implementation in `AddressBook.java` and `Person.java` only uses phone number to identify duplicates. Two people with the same email can be added successfully, but two people with the same phone number cannot. The DG needed to reflect this accurate behavior.

### Verification

- Build status: ✅ PASSING
- Tests affected: None (documentation only)
- Code changes required: None (implementation was already correct)

---

## Fix #2: Wedding Date Prefix Notation

**Date**: 3 November 2025  
**Issue**: DG documentation shows `date:` prefix but actual implementation uses `w/` (per User Guide and application).  
**Reference**: GitHub Issue - `type.DocumentationBug` - Opened 2 days ago

### Changes Made

**File**: `docs/DeveloperGuide.md`

**Location 1 - Key Components Section (Line 247)**
- **Before**: "`AddCommandParser` - Parses the `date:` prefix to extract wedding dates"
- **After**: "`AddCommandParser` - Parses the `w/` prefix to extract wedding dates"

**Location 2 - Extension Case 6a Error Message (Line 666)**
- **Before**: "KnotBook shows error: \"Wedding date is required for clients. Example: date:2025-10-12\""
- **After**: "KnotBook shows error: \"Wedding date is required for clients. Example: w/2025-10-12\""

**Location 3 - Test Case Example (Line 884)**
- **Before**: "`add n/John Doe p/98765432 e/john@example.com a/123 Street date:12-10-2025 type/client t/friends`"
- **After**: "`add n/John Doe p/98765432 e/john@example.com a/123 Street w/12-10-2025 type/client t/friends`"

### Status

✅ **FIXED** - All 3 occurrences of `date:` prefix updated to `w/` to match actual implementation and User Guide.

### Rationale

The User Guide clearly shows `w/` as the prefix for wedding dates in commands like:
```
add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 w/15-06-2025 type/client pr/Jane Doe
```

The DG must maintain consistency with the User Guide and actual implementation.

### Verification

- Build status: ✅ PASSING
- Tests affected: None (documentation only)
- Code changes required: None (implementation is correct, only DG examples needed updating)
- grep_search confirmed: No remaining `date:` prefix notation in DG examples

---

## Fix #3: Category Prefix Notation

**Date**: 3 November 2025  
**Issue**: DG test case examples show `t/` prefix for categories, but actual implementation uses `c/` (per User Guide and application).  
**Reference**: GitHub Issue - `type.DocumentationBug` - Opened 2 days ago

### Changes Made

**File**: `docs/DeveloperGuide.md`

**Location 1 - Client Test Case (Line 884)**
- **Before**: "`add n/John Doe p/98765432 e/john@example.com a/123 Street w/12-10-2025 type/client t/friends`"
- **After**: "`add n/John Doe p/98765432 e/john@example.com a/123 Street w/12-10-2025 type/client c/friends`"

**Location 2 - Vendor Test Case (Line 892)**
- **Before**: "`add n/Flower Shop p/91234567 e/flowers@example.com a/789 Road type/vendor t/florist`"
- **After**: "`add n/Flower Shop p/91234567 e/flowers@example.com a/789 Road type/vendor c/florist`"

### Status

✅ **FIXED** - All 2 occurrences of `t/` prefix in test cases updated to `c/` to match actual implementation and User Guide.

### Rationale

The User Guide shows `c/` as the prefix for categories in commands. The actual implementation uses the `c/` prefix (PREFIX_CATEGORY = "c/") and the `t/` prefix was removed in the category refactoring. The DG test case examples must use the correct prefix to match actual command syntax.

### Verification

- Build status: ✅ PASSING
- Tests affected: None (documentation only)
- Code changes required: None (implementation is correct, only DG examples needed updating)
- grep_search confirmed: No remaining `t/friends` or `t/florist` in DG examples

---

## Fix #4: Link/Unlink Command Formatting

**Date**: 3 November 2025  
**Issue**: DG examples show `link client/1, vendor/3` with a space after the comma, but the actual implementation expects `link client/1,vendor/3` without space.  
**Reference**: GitHub Issue - `type.DocumentationBug` - Opened 2 days ago

### Changes Made

**File**: `docs/DeveloperGuide.md`

**Location 1 - Usage Section (Lines 312-313)**
- **Before**: 
  ```
  link client/1, vendor/3
  unlink client/1, vendor/3
  ```
- **After**: 
  ```
  link client/1,vendor/3
  unlink client/1,vendor/3
  ```

**Location 2 - Link Test Cases (Lines 840, 843, 846)**
- **Before**: `link client/1, vendor/2`, `link client/0, vendor/1`, `link client/1, vendor/999`
- **After**: `link client/1,vendor/2`, `link client/0,vendor/1`, `link client/1,vendor/999`

**Location 3 - Unlink Test Cases (Lines 858, 861, 864)**
- **Before**: `unlink client/1, vendor/2`, `unlink client/0, vendor/1`, `unlink client/1, vendor/999`
- **After**: `unlink client/1,vendor/2`, `unlink client/0,vendor/1`, `unlink client/1,vendor/999`

### Status

✅ **FIXED** - All 9 occurrences of link/unlink commands updated to remove space after comma.

### Rationale

The parser expects the link/unlink command format without spaces: `client/INDEX,vendor/INDEX`. The spaces in the DG examples were causing a mismatch between documentation and actual implementation, leading to potential confusion for users copying examples directly from the DG.

### Verification

- Build status: ✅ PASSING
- Tests affected: None (documentation only)
- Code changes required: None (implementation was already correct, only DG examples needed updating)

---

## Summary Table

| # | Issue | Status | File(s) | Lines | Description |
|---|-------|--------|---------|-------|-------------|
| 1 | Duplicate detection | ✅ FIXED | DeveloperGuide.md | 632, 694 | Changed "phone or email" to "phone number" |
| 2 | Wedding date prefix | ✅ FIXED | DeveloperGuide.md | 247, 666, 884 | Updated `date:` notation to `w/` across all examples |
| 3 | Category prefix notation | ✅ FIXED | DeveloperGuide.md | 884, 892 | Changed `t/` prefix to `c/` in test case examples |
| 4 | Link/unlink formatting | ✅ FIXED | DeveloperGuide.md | 312, 313, 840, 843, 846, 858, 861, 864 | Removed space after comma in link/unlink commands |

---

## How to Update This Document

When adding new DG fixes:

1. Add a new section with:
   - Date of fix
   - Issue description
   - Reference (GitHub issue, discussion, or finding)
   
2. List all changes made with:
   - File path
   - Specific lines/sections
   - Before and After text
   
3. Include:
   - Rationale for the fix
   - Verification status (build, tests)
   - Whether code changes were needed
   
4. Update the Summary Table at the end

---

## Notes for Contributors

- DG fixes are typically documentation-only changes (no code changes needed)
- Always verify that the implementation matches the DG before assuming DG is wrong
- Keep User Guide and DG aligned for consistency
- Test cases in DG examples should match actual command syntax
