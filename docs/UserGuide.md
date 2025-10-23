---
layout: page
title: User Guide
---

KnotBook is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Knotbook can get your wedding contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T16-3/tp/releases/tag/v1.3).

1. Copy the file to the folder you want to use as the _home folder_ for your KnotBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

  * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 w/15-06-2020 type/client budget/5000-10000` : Adds a client named `John Doe` to the Address Book (clients can have a budget).

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a contact (client or vendor) to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS w/WEDDING_DATE type/(client|vendor) [price/PRICE] [budget/BUDGET] [t/TAG]…`

Notes:
* `type/` must be either `client` or `vendor`. If `type/` is omitted, the contact defaults to `client`.
* `price/` is only applicable to vendors. If provided for a non-vendor, the command will be rejected.
* `budget/` is only applicable to clients. If provided for a non-client, the command will be rejected.
* A person can have any number of tags (including 0).

Examples:
* Example (Client):
  `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 w/15-06-2020 type/client budget/5000-10000 t/friends`
  - Adds a client with a budget range of `5000-10000`.
* Example (Vendor):
  `add n/Jane Smith p/91234567 e/jane@example.com a/123 Orchard Road, #03-45 w/20-07-2020 type/vendor price/1000-2000 t/photographer`
  - Adds a vendor with a price range of `1000-2000`.

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits details of an existing contact in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [w/WEDDING_DATE] [type/(client|vendor)] [price/PRICE] [budget/BUDGET] [t/TAG]…`

Notes:
* Edits the person at the specified `INDEX`. `INDEX` refers to the index in the currently displayed person list and must be a positive integer.
* At least one editable field must be provided.
* Changing `type/` between client and vendor is allowed; however, `price/` only applies to vendors and `budget/` only applies to clients.
* When editing tags, the existing tags of the person will be replaced by the supplied tags.
* To clear all tags, use `t/` with no value.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` — updates phone and email of the 1st person.
* `edit 2 type/vendor price/1500-2500` — change the 2nd person's type to vendor and set their price range.
* `edit 3 type/client budget/3000-7000` — change the 3rd person's type to client and set their budget range.

### Linking a client and a vendor: `link`

Links a client to a vendor so their relationship appears in both profiles.

Format: `link [client/CLIENT_INDEX] [vendor/VENDOR_INDEX]`

Notes:
* Both `CLIENT_INDEX` and `VENDOR_INDEX` refer to indexes in the currently displayed person list.
* The person at `client/` must be a client; the person at `vendor/` must be a vendor. If not, the command will be rejected.

Example:
* `link client/3 vendor/5` — links the client at index 3 with the vendor at index 5.

### Unlinking a client and a vendor: `unlink`

Removes an existing link between a client and a vendor.

Format: `unlink [client/CLIENT_INDEX] [vendor/VENDOR_INDEX]`

Example:
* `unlink client/3 vendor/5` — removes the link between the client at index 3 and vendor at index 5.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

KnotBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

KnotBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, KnotBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the KnotBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous KnotBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

<table>
  <thead>
    <tr>
      <th>Action</th>
      <th>Format, Examples</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Add</strong></td>
      <td>
        <code>add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS w/WEDDING_DATE type/(client|vendor) [price/PRICE] [budget/BUDGET] [t/TAG]…</code>
        <br>
        <pre><code>add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd w/15-06-2020 type/client budget/5000-10000 t/friend t/colleague</code></pre>
      </td>
    </tr>
    <tr>
      <td><strong>Clear</strong></td>
      <td><code>clear</code></td>
    </tr>
    <tr>
      <td><strong>Delete</strong></td>
      <td><code>delete INDEX</code><br>e.g., <code>delete 3</code></td>
    </tr>
    <tr>
      <td><strong>Edit</strong></td>
      <td>
        <code>edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [w/WEDDING_DATE] [type/(client|vendor)] [price/PRICE] [budget/BUDGET] [t/TAG]…</code>
        <br>
        <pre><code>edit 2 n/James Lee e/jameslee@example.com
edit 2 type/vendor price/1500-2500</code></pre>
      </td>
    </tr>
    <tr>
      <td><strong>Find</strong></td>
      <td><code>find KEYWORD [MORE_KEYWORDS]</code><br>e.g., <code>find James Jake</code></td>
    </tr>
    <tr>
      <td><strong>List</strong></td>
      <td><code>list</code></td>
    </tr>
    <tr>
      <td><strong>Help</strong></td>
      <td><code>help</code></td>
    </tr>
    <tr>
      <td><strong>Link</strong></td>
      <td>
        <code>link client/CLIENT_INDEX vendor/VENDOR_INDEX</code>
        <br>
        <pre><code>link client/3 vendor/5</code></pre>
      </td>
    </tr>
    <tr>
      <td><strong>Unlink</strong></td>
      <td>
        <code>unlink client/CLIENT_INDEX vendor/VENDOR_INDEX</code>
        <br>
        <pre><code>unlink client/3 vendor/5</code></pre>
      </td>
    </tr>
  </tbody>
</table>
