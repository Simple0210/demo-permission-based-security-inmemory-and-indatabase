package uz.simplecode.rolebasedsecurityapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.simplecode.rolebasedsecurityapp.payload.ApiResponse;
import uz.simplecode.rolebasedsecurityapp.payload.req.GroupReqDto;
import uz.simplecode.rolebasedsecurityapp.payload.res.GroupResDto;
import uz.simplecode.rolebasedsecurityapp.service.GroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PreAuthorize("hasAuthority('GET_ALL_GROUP')")
  @GetMapping("/get")
  @Operation(summary = "Get all groups", description = "Barcha guruhlarni ro'yxatini ko'rish")
  public ResponseEntity<ApiResponse<List<GroupResDto>>> getAllGroups() {
    return groupService.getAllGroups();
  }

  @PreAuthorize("hasAuthority('GET_GROUP')")
  @GetMapping("/get/{id}")
  @Operation(summary = "Get group by id", description = "Id bo'yicha guruhni ko'rish")
  public ResponseEntity<ApiResponse<GroupResDto>> getGroupById(@PathVariable UUID id) {
    return groupService.getGroupById(id);
  }

  @PreAuthorize("hasAuthority('SAVE_GROUP')")
  @PostMapping("/save")
  @Operation(summary = "Save new group", description = "Yangi guruhni saqlash")
  public ResponseEntity<ApiResponse<String>> saveNewGroup(@RequestBody GroupReqDto groupReqDto) {
    return groupService.saveNewGroup(groupReqDto);
  }

  @PreAuthorize("hasAuthority('EDIT_GROUP')")
  @PutMapping("/edit/{id}")
  @Operation(summary = "Change group", description = "Guruh ma'lumotlarini taxrirlash")
  public ResponseEntity<ApiResponse<String>> editGroup(@PathVariable UUID id, @RequestBody GroupReqDto groupReqDto) {
    return groupService.editGroup(id, groupReqDto);
  }

  @PreAuthorize("hasAuthority('DELETE_GROUP')")
  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete group", description = "Guruh ma'lumotlarini o'chirish")
  public ResponseEntity<ApiResponse<String>> deleteGroup(@PathVariable UUID id) {
    return groupService.deleteGroup(id);
  }
}
