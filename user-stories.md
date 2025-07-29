# User Stories for Smart Clinic Management System

---

## Admin User Stories

### User Story: Manage Doctors

**Role:** Admin
**Feature:** Doctor Management

**As an** Admin,
**I want to** add, view, update, and delete doctor profiles,
**so that I can** maintain an accurate list of doctors in the system.

---

**Acceptance Criteria:**
* **Title:** Successful Doctor CRUD Operations.
* **Description:** The Admin should be able to perform all CRUD operations on doctor profiles.
* **Scenario 1: Add New Doctor**
    * **Given:** Admin provides valid doctor details (name, email, specialty, initial password, available times).
    * **When:** Admin submits the new doctor form.
    * **Then:** The new doctor profile is saved in the database and appears in the doctor list.
* **Scenario 2: View Doctor List**
    * **Given:** Doctors exist in the system.
    * **When:** Admin navigates to the doctor management section.
    * **Then:** All doctor profiles are displayed with their relevant details.
* **Scenario 3: Update Doctor Details**
    * **Given:** An existing doctor profile.
    * **When:** Admin modifies doctor details (e.g., specialty, available times) and saves changes.
    * **Then:** The doctor's profile is updated in the database.
* **Scenario 4: Delete Doctor Profile**
    * **Given:** An existing doctor profile with no active future appointments.
    * **When:** Admin requests to delete a doctor profile.
    * **Then:** The doctor's profile is removed from the database. (If active appointments, prompt for confirmation/reschedule).

**Priority:** High
**Story Points:** 8

---

### User Story: View System Statistics

**Role:** Admin
**Feature:** System Overview Dashboard

**As an** Admin,
**I want to** view system statistics such as total patients, total doctors, and daily/weekly appointments,
**so that I can** monitor the clinic's operational performance.

---

**Acceptance Criteria:**
* **Title:** Display of Key Performance Indicators.
* **Description:** The Admin dashboard should show relevant statistics at a glance.
* **Scenario 1: Access Dashboard**
    * **Given:** Admin is logged in.
    * **When:** Admin navigates to the dashboard.
    * **Then:** The dashboard displays real-time counts for total patients, total doctors, and upcoming appointments.
* **Scenario 2: Daily Appointment Summary**
    * **Given:** Appointments exist for the current day.
    * **When:** Admin views the dashboard.
    * **Then:** The dashboard displays the number of appointments scheduled for the current day.

**Priority:** Medium
**Story Points:** 5

---

## Doctor User Stories

### User Story: Manage Own Availability

**Role:** Doctor
**Feature:** Schedule Management

**As a** Doctor,
**I want to** specify my available time slots for appointments,
**so that patients can** book appointments only when I am available.

---

**Acceptance Criteria:**
* **Title:** Doctor Availability Configuration.
* **Description:** The Doctor can set and modify their daily/weekly availability.
* **Scenario 1: Set New Available Times**
    * **Given:** Doctor is logged in and navigates to the availability section.
    * **When:** Doctor adds new time slots (e.g., "09:00 AM - 12:00 PM") for a specific date.
    * **Then:** The new time slots are saved and become visible for patients to book.
* **Scenario 2: View Existing Availability**
    * **Given:** Doctor has previously set available times.
    * **When:** Doctor views their schedule.
    * **Then:** All previously set available time slots are displayed.
* **Scenario 3: Remove Availability**
    * **Given:** An existing available time slot.
    * **When:** Doctor removes a time slot.
    * **Then:** The time slot is no longer available for booking.

**Priority:** High
**Story Points:** 8

---

### User Story: View Patient Appointments

**Role:** Doctor
**Feature:** Appointment Schedule Viewing

**As a** Doctor,
**I want to** view my upcoming and past appointments with patient details,
**so that I can** prepare for consultations and manage my patient load.

---

**Acceptance Criteria:**
* **Title:** Doctor's Appointment Calendar.
* **Description:** The Doctor can easily access their appointment schedule.
* **Scenario 1: View Daily Appointments**
    * **Given:** Doctor is logged in and navigates to their appointment calendar.
    * **When:** Doctor selects a specific date.
    * **Then:** A list of all appointments for that date, including patient names and appointment times, is displayed.
* **Scenario 2: Access Patient Details from Appointment**
    * **Given:** Doctor is viewing an appointment.
    * **When:** Doctor clicks on a patient's name within an appointment.
    * **Then:** The patient's basic profile details (e.g., contact info) are displayed.

**Priority:** High
**Story Points:** 5

---

## Patient User Stories

### User Story: Book an Appointment

**Role:** Patient
**Feature:** Appointment Booking

**As a** Patient,
**I want to** browse available doctors and book an appointment online,
**so that I can** easily schedule my visit to the clinic.

---

**Acceptance Criteria:**
* **Title:** Successful Appointment Booking.
* **Description:** Patient can select a doctor, view their availability, and book an open slot.
* **Scenario 1: Search for Doctor**
    * **Given:** Patient is logged in and navigates to the appointment booking section.
    * **When:** Patient searches for doctors (e.g., by specialty).
    * **Then:** A list of matching doctors is displayed.
* **Scenario 2: View Doctor Availability**
    * **Given:** Patient has selected a doctor.
    * **When:** Patient clicks to view the doctor's calendar.
    * **Then:** The doctor's available time slots for upcoming days are displayed.
* **Scenario 3: Book Available Slot**
    * **Given:** Patient has selected an available time slot.
    * **When:** Patient confirms the booking.
    * **Then:** The appointment is saved, the time slot is marked as booked, and the patient receives a confirmation.

**Priority:** Critical
**Story Points:** 13

---

### User Story: View Own Appointments

**Role:** Patient
**Feature:** My Appointments

**As a** Patient,
**I want to** view my upcoming and past appointments,
**so that I can** keep track of my scheduled visits.

---

**Acceptance Criteria:**
* **Title:** Patient Appointment History.
* **Description:** The Patient can view a chronological list of their appointments.
* **Scenario 1: Access My Appointments**
    * **Given:** Patient is logged in.
    * **When:** Patient navigates to the "My Appointments" section.
    * **Then:** A list of all their scheduled (upcoming) and completed (past) appointments is displayed, showing doctor name, date, and time.
* **Scenario 2: Cancel Upcoming Appointment**
    * **Given:** An upcoming appointment.
    * **When:** Patient requests to cancel the appointment (within clinic's cancellation policy).
    * **Then:** The appointment is marked as cancelled, and the time slot becomes available again.

**Priority:** High
**Story Points:** 5