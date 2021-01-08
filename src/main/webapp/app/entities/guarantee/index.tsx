import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Guarantee from './guarantee';
import GuaranteeDetail from './guarantee-detail';
import GuaranteeUpdate from './guarantee-update';
import GuaranteeDeleteDialog from './guarantee-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GuaranteeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GuaranteeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GuaranteeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Guarantee} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GuaranteeDeleteDialog} />
  </>
);

export default Routes;
