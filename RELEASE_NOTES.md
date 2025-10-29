# KnotBook v1.0 Release Notes

## Overview
KnotBook v1.0 is a desktop contact management application specifically designed for wedding planners. Built upon the AddressBook Level 3 (AB3) foundation by SE-EDU, KnotBook transforms a general-purpose contact manager into a specialized tool for managing wedding clients and vendors.

## New Features

### Core Contact Management
- **Person Type System**: Distinguish between clients and vendors using the `type/` parameter
  - Clients: Wedding couples who need planning services
  - Vendors: Service providers (florists, caterers, photographers, venues, etc.)
- **Enhanced Add Command**: Add contacts with type-specific fields
  - Clients require wedding dates (`date:`)
  - Vendors can include pricing information (`price:`)
  - Clients can include budget information (`budget:`)

### Client-Vendor Relationship Management
- **Link Command**: Associate vendors with clients for specific weddings
  - Syntax: `link client/INDEX vendor/INDEX`
  - Track which vendors are assigned to which wedding events
- **Unlink Command**: Remove vendor-client associations
  - Syntax: `unlink client/INDEX vendor/INDEX`
  - Update relationships when vendors are changed or services cancelled

### Advanced Filtering & Search
- **Category Filter (Cat Command)**: Filter contacts by category/tag
  - Syntax: `cat CATEGORY`
  - Case-insensitive matching (e.g., `cat florist`, `cat PHOTOGRAPHER`)
  - Quickly view all vendors of a specific type
- **Enhanced Find Command**: Search contacts by name with improved matching

### Budget & Pricing Features
- **Budget Tracking**: Store and display client wedding budgets
  - Support for single values or ranges (e.g., `budget:5000` or `budget:3000-5000`)
  - Displayed in client contact cards
- **Vendor Pricing**: Track vendor quotes and pricing
  - Support for single values or ranges (e.g., `price:1000` or `price:800-1500`)
  - Displayed in vendor contact cards

### Wedding Planning Specifics
- **Wedding Date Management**: Required field for all client contacts
  - Format: `date:DD-MM-YYYY`
  - Helps track upcoming weddings and planning timelines

### User Interface Improvements
- **Enhanced Help Window**: Accordion-style interface with collapsible sections
  - All commands organized in expandable panels
  - Detailed usage instructions and examples for each command
  - Removed dependency on external help URLs
- **Improved Contact Display**: Visual distinction between clients and vendors
  - Type badges for easy identification
  - Budget display for clients
  - Price display for vendors
  - Link indicators showing client-vendor relationships
- **Person Details Panel**: Enhanced detail view with all contact information

### Data Management
- **JSON Storage**: All contact data persisted in JSON format
- **Data Validation**: Comprehensive input validation for all fields
  - Phone numbers: 8-digit Singapore numbers
  - Email: Standard email format validation
  - Dates: Proper date format validation
  - Budget/Price: Numeric or range validation
- **Duplicate Prevention**: Prevents duplicate contacts with same phone or email

## Technical Improvements
- **Type-Safe Person Types**: Enum-based implementation for CLIENT/VENDOR types
- **Enhanced Parser System**: Support for new prefixes and command formats
- **Comprehensive Test Coverage**: Unit tests for all new commands and features
- **Code Quality**: Adherence to CheckStyle standards

## Command Reference

### New Commands
- `cat CATEGORY` - Filter contacts by category
- `link client/INDEX vendor/INDEX` - Link a vendor to a client
- `unlink client/INDEX vendor/INDEX` - Unlink a vendor from a client

### Modified Commands
- `add` - Now supports `type/`, `date:`, `budget:`, and `price:` parameters
- `edit` - Can edit type-specific fields (budget, price, wedding date)
- `help` - Opens enhanced help window with accordion interface

### Existing Commands (from AB3)
- `list` - List all contacts
- `find KEYWORD` - Find contacts by name
- `delete INDEX` - Delete a contact
- `clear` - Clear all contacts
- `exit` - Exit the application

## System Requirements
- Java 17 or above
- Works on Windows, macOS, and Linux
- No internet connection required (offline-first design)

## Known Limitations
- Maximum 1000 contacts recommended for optimal performance
- Wedding dates must be in DD-MM-YYYY format
- Phone numbers limited to 8-digit Singapore format

## Attribution
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

## Team
Developed by Team T16-3 from NUS School of Computing:
- Clement Aditya Chendra ([@Cleaach](https://github.com/cleaach))
- Arul ([@xxdydx](https://github.com/xxdydx))
- Javier Lim ([@javierlimt6](https://github.com/javierlimt6))
- Dylan Ananda Astono ([@dylandaaa](https://github.com/dylandaaa))
- Josephine Celine Tjandra ([@CelineTj16](https://github.com/CelineTj16))

---

For detailed usage instructions, see the [User Guide](docs/UserGuide.md).
For technical documentation, see the [Developer Guide](docs/DeveloperGuide.md).
