# TASKS.md

Tracks `PROJECT_CONTEXT.md` §8 roadmap. Update the checkbox and add a one-line note when a
step is completed or partially completed.

- [x] 1. Environment check + Android project bootstrap + CI-like local build command
      → Local build blocked (see SESSION_HANDOFF.md). CI build command added instead:
      `.github/workflows/build-debug-apk.yml`.
- [~] 2. Room schema + repositories + local author profile
      → Schema/DAOs/database done. `AuthorRepository` + `ProjectRepository` done.
      Import-related repositories (XML/ZIP) deferred to steps 7/11.
- [~] 3. Projects + manual creation/edit/archive
      → Manual creation done (dialog + repository + ViewModel). Edit/archive UI not built yet.
- [ ] 4. Core Entry flows without photos
- [ ] 5. Camera/gallery + photo persistence
- [ ] 6. Entry detail/edit/delete/revision
- [ ] 7. XML import preview + commit + BOQ selection
- [ ] 8. Timeline/search/filters + Today
- [ ] 9. Gantt
- [ ] 10. Entry/day/full project export/share
- [ ] 11. Full project ZIP re-import/restore
- [ ] 12. QA/data integrity/performance
- [ ] 13. Build debug APK and install-test → workflow exists, not yet run/verified by a human
- [ ] 14. Prepare signed release APK workflow
- [ ] 15. Update user manual to exact shipped UI
