import React from "react";
import { Button, TextField, Rating } from "@mui/material";
import { Formik, Form } from "formik";
import type { FormikHelpers } from "formik";
import * as Yup from "yup";

/* ---------- Types ---------- */
interface ReviewFormValues {
  rating: number;
  comment: string;
}

/* ---------- Validation ---------- */
const validationSchema = Yup.object({
  rating: Yup.number()
    .min(1, "Please give at least 1 star")
    .required("Rating is required"),
  comment: Yup.string()
    .required("Review is required")
    .min(10, "Review must be at least 10 characters"),
});

const AddReview: React.FC = () => {
  const initialValues: ReviewFormValues = {
    rating: 0,
    comment: "",
  };

  const handleSubmit = (
    values: ReviewFormValues,
    { resetForm }: FormikHelpers<ReviewFormValues>
  ): void => {
    console.log("Review submitted:", values);
    // call API here
    resetForm();
  };

  return (
    <div className="pt-10 max-w-xl">
      <h2 className="text-2xl font-semibold mb-5">
        Add Review
      </h2>

      <div className="border rounded-lg p-6 shadow-sm bg-white">
        <Formik<ReviewFormValues>
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({
            values,
            errors,
            touched,
            setFieldValue,
          }) => (
            <Form className="space-y-5">
              {/* Rating */}
              <div>
                <p className="font-medium text-gray-700 mb-2">
                  Your Rating
                </p>
                <Rating
                  value={values.rating}
                  onChange={(_, value: number | null) =>
                    setFieldValue("rating", value ?? 0)
                  }
                />
                {touched.rating && errors.rating && (
                  <p className="text-sm text-red-500 mt-1">
                    {errors.rating}
                  </p>
                )}
              </div>

              {/* Comment */}
              <TextField
                name="comment"
                label="Your Review"
                multiline
                rows={4}
                fullWidth
                size="small"
                value={values.comment}
                onChange={(e) =>
                  setFieldValue("comment", e.target.value)
                }
                error={touched.comment && Boolean(errors.comment)}
                helperText={touched.comment && errors.comment}
              />

              {/* Actions */}
              <div className="flex justify-end gap-3 pt-2">
                <Button
                  type="reset"
                  variant="outlined"
                  color="inherit"
                  sx={{ textTransform: "none" }}
                >
                  Cancel
                </Button>

                <Button
                  type="submit"
                  variant="contained"
                  color="success"
                  sx={{ textTransform: "none" }}
                >
                  Submit Review
                </Button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default AddReview;
